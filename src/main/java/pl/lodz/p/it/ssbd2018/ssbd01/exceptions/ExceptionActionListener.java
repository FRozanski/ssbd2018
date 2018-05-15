/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 *
 * @author agkan
 */
public class ExceptionActionListener extends ActionListenerImpl implements ActionListener{

    private final String errorOccured = bundle.getString("ErrorOccured");
    private final String outCome = "naviToErrorPage";
    
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        try {
            super.processAction(event);
        } catch (FacesException fe) {
            Logger lg = Logger.getLogger("javax.enterprise.system.container.web.faces");
            lg.log(Level.SEVERE, this.getClass() + ": " + errorOccured +  ": ", fe);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Application application = facesContext.getApplication();
            NavigationHandler navigationHandler = application.getNavigationHandler();
            navigationHandler.handleNavigation(facesContext, null, outCome);
            facesContext.renderResponse();
        }
    }    
}
