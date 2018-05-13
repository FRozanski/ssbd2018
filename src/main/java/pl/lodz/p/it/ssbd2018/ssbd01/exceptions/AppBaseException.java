package pl.lodz.p.it.ssbd2018.ssbd01.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author agkan
 */
@ApplicationException(rollback = true)
public class AppBaseException extends Exception{
    
}
