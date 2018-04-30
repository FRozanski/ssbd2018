/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author fifi
 */
@ManagedBean
@RequestScoped
public class EditAccountBean {

    public EditAccountBean() {
    }
    
    @ManagedProperty(value = "#{listAccountBean}")
    private ListAccountBean listAccountBean;
    
    public void setListAccountBean(ListAccountBean listAccountBean) {
        this.listAccountBean = listAccountBean;
    }
}
