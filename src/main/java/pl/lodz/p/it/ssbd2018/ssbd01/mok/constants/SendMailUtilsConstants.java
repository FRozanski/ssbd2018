/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.constants;

/**
 *
 * @author Filip
 */
public class SendMailUtilsConstants {
    
    private int PORT;
    private String HOST;
    private String FROM;
    private boolean AUTH;
    private String USERNAME;
    private String PASSWORD;
    private String PROTOCOL;
    private boolean DEBUG;
    private String SUBJECT;
    private String WELCOME_MESSAGE;
    private String VERYFICATION_MESSAGE;
    private String ACTIVATION_MESSAGE;
    private String LOCK_MESSAGE;
    private String GREETINGS;

    public int getPORT() {
        return PORT;
    }

    public String getHOST() {
        return HOST;
    }

    public String getFROM() {
        return FROM;
    }

    public boolean isAUTH() {
        return AUTH;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getPROTOCOL() {
        return PROTOCOL;
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public String getWELCOME_MESSAGE() {
        return WELCOME_MESSAGE;
    }

    public String getVERYFICATION_MESSAGE() {
        return VERYFICATION_MESSAGE;
    }

    public String getACTIVATION_MESSAGE() {
        return ACTIVATION_MESSAGE;
    }

    public String getLOCK_MESSAGE() {
        return LOCK_MESSAGE;
    }

    public String getGREETINGS() {
        return GREETINGS;
    }
}
