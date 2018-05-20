package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok;

/**
 *
 * @author agkan
 */
public class AccountException extends MokBaseException {
    
    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".account"; 
    }
}
