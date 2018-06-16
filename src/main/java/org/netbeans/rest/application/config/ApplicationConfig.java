/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author agkan
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        //addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.EJBAccessExceptionMapper.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.JsonParsingExceptionMapper.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.NoSuchElementExceptionMapper.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.ProcessingExceptionMapper.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.mok.rest.AccountRestController.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.mok.rest.AuthRestController.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.mok.rest.SessionRestController.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.mop.rest.CategoryRestController.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.mop.rest.ProductRestController.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.moz.rest.OrderRestController.class);
        resources.add(pl.lodz.p.it.ssbd2018.ssbd01.moz.rest.ShippingRestController.class);
    }
    
}
