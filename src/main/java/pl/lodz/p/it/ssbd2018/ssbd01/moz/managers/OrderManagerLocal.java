/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs manager do zarządzania zamówieniami
 * @author fifi
 */
@Local
public interface OrderManagerLocal {    
    
    /**
     * Metoda umożliwiająca zamówienie produktu przez użytkownika.
     * @param productId
     * @param qty
     * @param shippingId
     * @param login
     * @throws AppBaseException
     */
    public void makeOrder(long productId, String qty, long shippingId, String login) throws AppBaseException;      
    
    /**
     * Metoda umożliwiająca zmianę statusu zamówienia.
     * @param order
     * @param orderStatus
     * @throws AppBaseException
     */
    void setOrderStatus(Order1 order, OrderStatus orderStatus) throws AppBaseException;
     
    /**
     * Metoda umożliwiająca pobranie zamówienia po ID.
     * @param id
     * @return
     * @throws AppBaseException
     */
    public Order1 getOrder1ById(long id) throws AppBaseException;
    
    /**
     * Metoda umożliwiająca pobranie statusu zamówienia po ID.
     * @param id
     * @return
     * @throws AppBaseException
     */
    public OrderStatus getOrderStatusById(long id) throws AppBaseException;
    
    /**
     * Metoda umożliwiająca pobranie wszystkich zamówień.
     * @return type List
     */
    List<Order1> getAllOrders();
    
    /**
     * Metoda umożliwiająca pobranie zamówień jako kupujący.
     * @param login
     * @return
     * @throws AppBaseException
     */
    public List<Order1> getAllByBuyer(String login) throws AppBaseException;
            
    /**
     * Metoda umożliwiająca pobranie zamówień jako sprzedający.
     * @param login
     * @return
     * @throws AppBaseException
     */
    public List<Order1> getAllBySeller(String login) throws AppBaseException;
    
    List<OrderStatus> getAllOrderStatus();
    
}
