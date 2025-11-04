package atm.bank;

import atm.bank.enums.TransactionType;

import java.math.BigDecimal;

public class WithdrawTransaction implements Transaction {
    Account account;
    BigDecimal amount;

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
    }

    @Override
    public boolean validateTransaction() {
        assert account != null;
        return account.getBalance().compareTo(amount) > 0;
    }

    public WithdrawTransaction(Account account, BigDecimal amount) {
        if (!validateTransaction()) {
            throw new IllegalStateException("Cannot complete withdrawal: Insufficient funds in account");
        }
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void executeTransaction() {
        account.updateBalanceWithTransaction(amount.negate());
    }
}
