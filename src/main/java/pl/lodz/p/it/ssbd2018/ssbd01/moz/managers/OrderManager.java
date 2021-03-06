/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderProducts;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderShipping;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.ProductNotEnougthException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.OrderNullQuantityException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.OrderQtyException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.OrderQtyFormatException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.OrderQtyTooLowException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.ProductFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.UnitFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.ShippingMethodFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.LoggerInterceptor;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.Order1FacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.OrderProductsFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.OrderShippingFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.OrderStatusFacadeLocal;
/**
 * Klasa obsługująca zarządzanie obiektami typu {@link OrderProducts} {@link Order1}
 * @author fifi
 * @author dlange
 * @author piotrek
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@Interceptors(LoggerInterceptor.class)
public class OrderManager implements OrderManagerLocal{
    
    @EJB
    private ShippingMethodFacadeLocal shippingMethodFacade;
    
    @EJB
    private Order1FacadeLocal orderFacade;
    
    @EJB
    private OrderStatusFacadeLocal orderStatusFacade;
    
    @EJB(beanName = "ProductMOZ")
    private ProductFacadeLocal productFacade;
    
    @EJB
    private OrderProductsFacadeLocal orderProductsFacade;
    
    @EJB(beanName = "UnitMOZ")
    private UnitFacadeLocal unitFacade;
    
    @EJB(beanName = "AccountMOZ")
    private AccountFacadeLocal accountFacade;    
    
    @EJB
    private OrderShippingFacadeLocal orderShippingFacade;
    
    @Override
    @RolesAllowed("getAllOrders")
    public List<Order1> getAllOrders() {
        return orderFacade.findAll();
    }
    
    @Override
    @RolesAllowed("getAllByBuyer")
    public List<Order1> getAllByBuyer(String login) throws AppBaseException {
        return orderFacade.findByBuyerLogin(login);
    }
    
    @Override
    @RolesAllowed("getAllBySeller")
    public List<Order1> getAllBySeller(String login) throws AppBaseException {
        return orderFacade.findBySellerLogin(login);
    }
    
    @Override
    @RolesAllowed("getAllOrderStatus")
    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusFacade.findAll();
    }

    @Override
    @RolesAllowed("makeOrder")
    public void makeOrder(long productId, String quantity, long shippingId, String login) throws AppBaseException{
        Product product = productFacade.find(productId);
        Unit unit = unitFacade.find(product.getUnitId().getId());
        
        checkIfNotNull(quantity);
        checkIfMatchesDouble(quantity);
        
        Double qty = Double.parseDouble(quantity);
        checkIfNotBelowOrEqZero(qty);
        checkIfNotTooLow(qty);
        
        Account account = accountFacade.findByLogin(login);
        ShippingMethod shippingMethod = shippingMethodFacade.find(shippingId);
        
        Double newQty = product.getQty().doubleValue() - qty;
        checkIfNotBelowZero(newQty);
        
        OrderStatus os = orderStatusFacade.find((long) 1);
        Order1 order1 = new Order1();
        OrderShipping orderShipping = new OrderShipping();
        OrderProducts orderProducts = new OrderProducts();
        Double totalPrice = product.getPrice().doubleValue() * qty + shippingMethod.getPrice().doubleValue();
        
        orderShipping.setName(account.getName());
        orderShipping.setSurname(account.getSurname());
        orderShipping.setStreetNumber(account.getStreetNumber());
        orderShipping.setStreet(account.getStreet());
        orderShipping.setShippingMethodName(shippingMethod.getName());
        orderShipping.setPostalCode(account.getPostalCode());
        orderShipping.setFlatNumber(account.getFlatNumber());
        orderShipping.setCountry(account.getCountry());
        orderShipping.setCity(account.getCity());
        orderShippingFacade.create(orderShipping);
        
        order1.setBuyerId(account);
        order1.setSellerId(product.getOwnerId());
        order1.setShippingId(orderShipping);
        order1.setIsClosed(false);
        order1.setOrderPlacedDate(generateCurrentDate());
        order1.setStatusId(os);
        order1.setTotalPrice(BigDecimal.valueOf(totalPrice));
        orderFacade.create(order1);
        
        orderProducts.setProductName(product.getName());
        orderProducts.setProductQty(BigDecimal.valueOf(qty));
        orderProducts.setProductUnitName(unit.getUnitName());
        orderProducts.setProductValue(product.getPrice());
        orderProducts.setOrderId(order1);
        orderProductsFacade.create(orderProducts);
        
        product.setQty(BigDecimal.valueOf(newQty).setScale(3, RoundingMode.HALF_UP));
        productFacade.edit(product);
        
        long newNumOfOrders = account.getNumberOfOrders() + 1;
        account.setNumberOfOrders(newNumOfOrders);
        accountFacade.edit(account);
    }
    
    private void checkIfNotNull(String quantity) throws AppBaseException {
        if (quantity == null) {
            throw  new OrderNullQuantityException("qty_null_passed");
        }
    }
    
    private void checkIfNotTooLow(Double quantity) throws AppBaseException {
        BigDecimal scaledQty = BigDecimal.valueOf(quantity).setScale(3, RoundingMode.HALF_UP);
        if (scaledQty.doubleValue() <= 0.000) {
            throw new OrderQtyTooLowException("qty_too_low");
        }
    }
    
    private void checkIfMatchesDouble(String numberStr) throws AppBaseException{
        if(!numberStr.matches("^[0-9]+(\\.[0-9]+)?$")) {
            throw new OrderQtyFormatException("qty_format_error");
        }
    }
    
    private void checkIfNotBelowOrEqZero(Double number) throws AppBaseException{
        if (number <= 0.0) {
            throw new OrderQtyException("order_qty_zero_or_lower");
        }
    }
    
    private void checkIfNotBelowZero(Double number) throws AppBaseException{
        if (number < 0.0) {
            throw new ProductNotEnougthException("product_qty_not_enougth");
        }
    }

    @Override
    @RolesAllowed("setOrderStatus")
    public void setOrderStatus(Order1 order, OrderStatus orderStatus) throws AppBaseException {
        order.setStatusId(orderStatus);
        orderFacade.edit(order);
    }
    
    /**
     * Metoda używana do pobrania zamówienia ze źródła danych na podstawie jego id 
     * @param id typu long
     * @return type Order1
     * @throws AppBaseException 
     */
    @Override
    @RolesAllowed("getOrder1ById")
    public Order1 getOrder1ById(long id) throws AppBaseException {
        Order1 order = this.orderFacade.find(id); 
        return order;
    }
    
    /**
     * Metoda używana do pobrania statusu zamówienia ze źródła danych na podstawie jego id 
     * @param id typu long
     * @return type OrderStatus
     * @throws AppBaseException 
     */
    @Override
    @RolesAllowed("getOrderStatusById")
    public OrderStatus getOrderStatusById(long id) throws AppBaseException {
        OrderStatus orderStatus = this.orderStatusFacade.find(id);
        return orderStatus;
    }
    
    private Date generateCurrentDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        return new Date(calendar.getTime().getTime());
    }
}
