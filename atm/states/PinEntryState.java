package atm.states;

import atm.ATMMachine;

public class PinEntryState extends ATMState {
    
    @Override
    public void processCardEjection(ATMMachine atmMachine) {
        atmMachine.getDisplay().showMessage("Card ejected");
        atmMachine.transitionToState(new IdleState());
    }

    
    @Override
    public void processPinEntry(ATMMachine atmMachine, String pin) {
        String cardNumber = atmMachine.getCardProcessor().getCardNumber();

        boolean isPinCorrect = atmMachine.getBankInterface().checkPin(cardNumber, pin);

        if (isPinCorrect) {
            atmMachine.getDisplay().showMessage("PIN correct, select transaction type");
            atmMachine.transitionToState(new TransactionSelectionState());
        } else {
            atmMachine.getDisplay().showMessage("Invalid PIN. Please try again");
        }
    }
}
