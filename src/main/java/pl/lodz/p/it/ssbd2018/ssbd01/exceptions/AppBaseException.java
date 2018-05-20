package pl.lodz.p.it.ssbd2018.ssbd01.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author agkan
 */
@ApplicationException(rollback = true)
public abstract class AppBaseException extends Exception{

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return "app.exception";
    }
}
