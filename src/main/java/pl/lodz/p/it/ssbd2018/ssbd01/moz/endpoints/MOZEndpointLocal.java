/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.endpoints;

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

/**
 *
 * @author fifi
 */
@Local
public interface MOZEndpointLocal {    
    
    void makeOrder(OrderProducts orderProducts);
    
    void makeOrderPayment(Order1 order);
    
    void cancelOrder(Order1 order);
    
    void addShippingMethod(ShippingMethod shippingMethod);
    
    void setShippingMethodActive(ShippingMethod shippingMethod, boolean active);
    
    void setOrderStatus(Order1 order, OrderStatus orderStatus);
    
    List<OrderProducts> getAllOrders();
    
    List<OrderProducts> getAllOrdersByAccountAsSeller(Account seller);
    
    List<OrderProducts> getAllOrdersByAccountAsBuyer(Account buyer);
    
    List<OrderProducts> getAllOrdersByDateAndPrice(Date date, BigDecimal price);
}
