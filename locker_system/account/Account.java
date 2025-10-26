package locker_system.account;

import java.math.BigDecimal;


public class Account {
    private final String accountId;
    private final String ownerName;
    private final AccountLockerPolicy lockerPolicy;
    private BigDecimal usageCharges = new BigDecimal("0.00");

    public Account(String accountId, String ownerName, AccountLockerPolicy lockerPolicy) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.lockerPolicy = lockerPolicy;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public AccountLockerPolicy getLockerPolicy() {
        return lockerPolicy;
    }

    public String getAccountId() {
        return accountId;
    }

    public void addUsageCharge(BigDecimal amount) {
        usageCharges = usageCharges.add(amount);
    }

    public BigDecimal getUsageCharges() {
        return usageCharges;
    }
}
