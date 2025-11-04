package atm.bank;

import atm.bank.enums.TransactionType;

import java.math.BigDecimal;

public class DepositTransaction implements Transaction {
    final Account account;
    final BigDecimal amount;

    @Override
    public TransactionType getType() {
        return TransactionType.DEPOSIT;
    }

    @Override
    public boolean validateTransaction() {
        return true;
    }

    public DepositTransaction(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void executeTransaction() {
        account.updateBalanceWithTransaction(amount);
    }
}
