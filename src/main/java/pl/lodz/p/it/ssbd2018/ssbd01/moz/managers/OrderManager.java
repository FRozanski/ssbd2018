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
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderProducts;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.ShippingMethodFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.LoggerInterceptor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Klasa obsługująca zarządzanie obiektami typu {@link OrderProducts} {@link Order1}
 * @author fifi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@Interceptors(LoggerInterceptor.class)
public class OrderManager implements OrderManagerLocal{
    
    @EJB
    private ShippingMethodFacadeLocal shippingMethodFacade;
    
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
    @RolesAllowed("setOrderStatus")
    public void setOrderStatus(Order1 order, OrderStatus orderStatus) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrders")
    public List<OrderProducts> getAllOrders() {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrdersByAccountAsSeller")
    public List<OrderProducts> getAllOrdersByAccountAsSeller(Account seller) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrdersByAccountAsBuyer")
    public List<OrderProducts> getAllOrdersByAccountAsBuyer(Account buyer) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllOrdersByDateAndPrice")
    public List<OrderProducts> getAllOrdersByDateAndPrice(Date date, BigDecimal price) {
        throw new NotImplementedException();
    }        
}
