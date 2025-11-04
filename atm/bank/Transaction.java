package atm.bank;

import atm.bank.enums.TransactionType;

public interface Transaction {
    TransactionType getType();

    boolean validateTransaction();

    void executeTransaction();
}
