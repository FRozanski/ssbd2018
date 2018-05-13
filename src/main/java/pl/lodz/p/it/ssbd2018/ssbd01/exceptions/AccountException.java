package pl.lodz.p.it.ssbd2018.ssbd01.exceptions;

import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author agkan
 */
public class AccountException extends AppBaseException {
    
    public static final String KEY_OPTIMISTIC_LOCK = "error.veryfication.optimistic.lock";
    public static final String KEY_DB_CONSTRAINT = "error.veryfication.db.constraint";

    private Account account;
    
    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    private AccountException(String message) {
        super(message);
    }
    
    public static AccountException createAccountExceptionWithOptimisticLock(
            Throwable cause, Account account) {
        AccountException ae = new AccountException(KEY_OPTIMISTIC_LOCK, cause);
        ae.setAccount(account);
        return ae;
    }
    
    public static AccountException createAccountExceptionWithDbConstraint(
            Throwable cause, Account account) {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT, cause);
        ae.setAccount(account);
        return ae;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }   
}
