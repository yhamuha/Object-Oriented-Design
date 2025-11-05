package atm.hardware.input;

import atm.ATMMachine;
import atm.bank.enums.TransactionType;

import java.math.BigDecimal;

public interface Keypad {

    void handlePinEntry(String pin, ATMMachine machine);

    void handleAmountEntry(BigDecimal amount, ATMMachine machine);

    void handleSelectTransaction(TransactionType transactionType, ATMMachine atmMachine);
}
