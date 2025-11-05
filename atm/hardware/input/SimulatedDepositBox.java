package atm.hardware.input;

import atm.ATMMachine;

import java.math.BigDecimal;

public class SimulatedDepositBox implements DepositBox {

    @Override
    public void acceptDeposit(BigDecimal amount, ATMMachine atmMachine) {
        atmMachine.collectDeposit(amount);
    }
}
