package atm.states;

import atm.ATMMachine;

public class IdleState extends ATMState {
    
    @Override
    public void processCardInsertion(ATMMachine atmMachine, String cardNumber) {
        if (atmMachine.getBankInterface().validateCard(cardNumber)) {
            atmMachine.getDisplay().showMessage("Please enter your PIN");
            atmMachine.transitionToState(new PinEntryState());
        } else {
            atmMachine.getDisplay().showMessage("Invalid card. Please try again.");
        }
    }
}
