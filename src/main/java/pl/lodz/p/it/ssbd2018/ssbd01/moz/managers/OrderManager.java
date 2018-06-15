/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderProducts;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.Order1FacadeLocal;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/**
 *
 * @author fifi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class OrderManager implements OrderManagerLocal{
    
    @EJB
    Order1FacadeLocal orderFacade;
    
    
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
    @RolesAllowed("makeOrder")
    public void makeOrder(OrderProducts orderProducts) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("makeOrderPayment")
    public void makeOrderPayment(Order1 order) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("cancelOrder")
    public void cancelOrder(Order1 order) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("addShippingMethod")
    public void addShippingMethod(ShippingMethod shippingMethod) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("setShippingMethodActive")
    public void setShippingMethodActive(ShippingMethod shippingMethod, boolean active) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("setOrderStatus")
    public void setOrderStatus(Order1 order, OrderStatus orderStatus) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrdersByAccountAsSeller")
    public List<Order1> getAllOrdersByAccountAsSeller(Account seller) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrdersByAccountAsBuyer")
    public List<Order1> getAllOrdersByAccountAsBuyer(Account buyer) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrdersByDateAndPrice")
    public List<Order1> getAllOrdersByDateAndPrice(Date date, BigDecimal price) {
        throw new NotImplementedException();
    }        
}
