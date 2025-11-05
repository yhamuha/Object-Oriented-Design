package atm.hardware.input;

import atm.ATMMachine;
import atm.bank.enums.TransactionType;

import java.math.BigDecimal;

public class SimulatedKeyPad implements Keypad {

    @Override
    public void handlePinEntry(String pin, ATMMachine atmMachine) {
        atmMachine.enterPin(pin);
    }

    @Override
    public void handleAmountEntry(BigDecimal amount, ATMMachine atmMachine) {
        atmMachine.enterAmount(amount);
    }

    @Override
    public void handleSelectTransaction(TransactionType transactionType, ATMMachine atmMachine) {
        switch (transactionType) {
            case WITHDRAW:
                atmMachine.withdrawRequest();
                break;
            case DEPOSIT:
                atmMachine.depositRequest();
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type: " + transactionType);
        }
    }
}
