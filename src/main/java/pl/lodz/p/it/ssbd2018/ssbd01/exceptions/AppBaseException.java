package pl.lodz.p.it.ssbd2018.ssbd01.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author agkan
 */
@ApplicationException(inherited = true, rollback = true)
public class AppBaseException extends Exception{

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }    
}
