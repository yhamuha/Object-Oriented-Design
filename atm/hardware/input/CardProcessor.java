package atm.hardware.input;

import atm.ATMMachine;

public interface CardProcessor {

    void handleCardInsertion(String cardNumber, ATMMachine atmMachine);

    void handleCardEjection(ATMMachine atmMachine);

    String getCardNumber();
}
