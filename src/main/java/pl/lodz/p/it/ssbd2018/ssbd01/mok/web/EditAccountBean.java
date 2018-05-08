/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author fifi
 */
@Named
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
