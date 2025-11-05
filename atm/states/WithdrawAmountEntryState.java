package atm.states;

import atm.ATMMachine;
import atm.bank.Account;

import java.math.BigDecimal;

public class WithdrawAmountEntryState extends ATMState {

    @Override
    public void processCardEjection(ATMMachine atmMachine) {
        atmMachine.getDisplay().showMessage("Transaction cancelled, card ejected");
        atmMachine.transitionToState(new IdleState());
    }

    @Override
    public void processAmountEntry(ATMMachine atmMachine, BigDecimal amount) {
        String cardNumber = atmMachine.getCardProcessor().getCardNumber();
        Account account = atmMachine.getBankInterface().getAccountByCard(cardNumber);
        boolean isSuccess = atmMachine.getBankInterface().withdrawFunds(account, amount);

        if (isSuccess) {
            atmMachine.getCashDispenser().dispenseCash(amount);
            atmMachine.getDisplay().showMessage("Please take your cash.");
        } else {
            atmMachine.getDisplay().showMessage("Insufficient funds, please try again.");
        }
        atmMachine.transitionToState(new TransactionSelectionState());
    }
}
