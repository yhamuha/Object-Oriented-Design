package atm.hardware.input;

import atm.ATMMachine;

import java.math.BigDecimal;

public interface DepositBox {

    void acceptDeposit(BigDecimal amount, ATMMachine machine);
}
