package atm.states;

import atm.ATMMachine;

public class TransactionSelectionState extends ATMState {

    
    @Override
    public void processCardEjection(ATMMachine atmMachine) {
        atmMachine.getDisplay().showMessage("Card ejected, transaction cancelled.");
        atmMachine.transitionToState(new IdleState());
    }

    
    @Override
    public void processWithdrawalRequest(ATMMachine atmMachine) {
        atmMachine.getDisplay().showMessage("Enter amount to withdraw:");
        atmMachine.transitionToState(new WithdrawAmountEntryState());
    }

    
    @Override
    public void processDepositRequest(ATMMachine atmMachine) {
        atmMachine.getDisplay().showMessage("Please deposit cash into the deposit box.");
        atmMachine.transitionToState(new DepositCollectionState());
    }
}
