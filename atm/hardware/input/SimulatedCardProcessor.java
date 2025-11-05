package atm.hardware.input;

import atm.ATMMachine;

public class SimulatedCardProcessor implements CardProcessor {

    private String cardNumber;

    @Override
    public void handleCardInsertion(String cardNumber, ATMMachine atmMachine) {
        this.cardNumber = cardNumber;
        atmMachine.insertCard(cardNumber);
    }

    @Override
    public void handleCardEjection(ATMMachine atmMachine) {
        this.cardNumber = null;
        atmMachine.ejectCard();
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }
}
