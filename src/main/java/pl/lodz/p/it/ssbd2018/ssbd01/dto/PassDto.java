package pl.lodz.p.it.ssbd2018.ssbd01.dto;

/**
 *
 * @author piotrek
 */
public class PassDto {
    
    private String accountId;
    private String oldPass;
    private String newPassOne;
    private String newPassTwo;
    
    public PassDto() {
    }
    
    public PassDto(String accountId, String oldPass, String newPassOne, String newPassTwo) {
        this.accountId = accountId;
        this.oldPass = oldPass;
        this.newPassOne = newPassOne;
        this.newPassTwo = newPassTwo;
    }
    
    public String getAccountId() {
        return accountId;
    }

    public String getOldPass() {
        return oldPass;
    }

    public String getNewPassOne() {
        return newPassOne;
    }

    public String getNewPassTwo() {
        return newPassTwo;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public void setNewPassOne(String newPassOne) {
        this.newPassOne = newPassOne;
    }

    public void setNewPassTwo(String newPassTwo) {
        this.newPassTwo = newPassTwo;
    } 
}