/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderProducts;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
@Local
public interface OrderManagerLocal {    
    
    void makeOrder(OrderProducts orderProducts);
    
    void makeOrderPayment(Order1 order);
    
    void cancelOrder(Order1 order);
    
    void addShippingMethod(ShippingMethod shippingMethod);
    
    void setShippingMethodActive(ShippingMethod shippingMethod, boolean active);
    
    void setOrderStatus(Order1 order, OrderStatus orderStatus);
    
    List<Order1> getAllOrders();
    
    public List<Order1> getAllByBuyer(String login) throws AppBaseException;
            
    public List<Order1> getAllBySeller(String login) throws AppBaseException;
    
    List<OrderStatus> getAllOrderStatus();
    
    List<Order1> getAllOrdersByAccountAsSeller(Account seller);
    
    List<Order1> getAllOrdersByAccountAsBuyer(Account buyer);
    
    List<Order1> getAllOrdersByDateAndPrice(Date date, BigDecimal price);
}
