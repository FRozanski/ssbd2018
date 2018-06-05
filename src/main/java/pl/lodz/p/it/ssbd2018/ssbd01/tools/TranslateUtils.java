/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.tools;

import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author agkan
 */
public class TranslateUtils extends ResourceBundle {
    
    protected static final String BUNDLE_NAME = "msg.translate";
    protected static final String BUNDLE_EXTENSION = "properties";
    protected static final Control UTF8_CONTROL = new UTF8Control(BUNDLE_EXTENSION);
    
    public TranslateUtils() {
        setParent(ResourceBundle.getBundle(BUNDLE_NAME,
                FacesContext.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL));
    }
    
    @Override
    protected Object handleGetObject(String key) {
        return parent.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }
    
}
