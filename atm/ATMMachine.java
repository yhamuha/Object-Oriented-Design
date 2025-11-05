package atm;

import atm.bank.Bank;
import atm.bank.BankInterface;
import atm.hardware.input.CardProcessor;
import atm.hardware.input.DepositBox;
import atm.hardware.input.Keypad;
import atm.hardware.output.CashDispenser;
import atm.hardware.output.Display;
import atm.states.ATMState;
import atm.states.IdleState;

import java.math.BigDecimal;

public class ATMMachine {
    private ATMState state;

    private final CardProcessor cardProcessor;
    private final DepositBox depositBox;
    private final CashDispenser cashDispenser;
    private final Keypad keypad;
    private final Display display;

    private final Bank bank;

    public ATMMachine(Bank bank, CardProcessor cardProcessor, DepositBox depositBox,
                      CashDispenser cashDispenser, Keypad keypad, Display display) {
        this.bank = bank;
        this.cardProcessor = cardProcessor;
        this.depositBox = depositBox;
        this.cashDispenser = cashDispenser;
        this.keypad = keypad;
        this.display = display;
        this.state = new IdleState();
    }

    public void insertCard(String cardNumber) {
        state.processCardInsertion(this, cardNumber);
    }

    public void ejectCard() {
        state.processCardEjection(this);
    }

    public void enterPin(String pin) {
        state.processPinEntry(this, pin);
    }

    public void withdrawRequest() {
        state.processWithdrawalRequest(this);
    }

    public void depositRequest() {
        state.processDepositRequest(this);
    }

    public void enterAmount(BigDecimal amount) {
        state.processAmountEntry(this, amount);
    }

    public void collectDeposit(BigDecimal amount) {
        state.processDepositCollection(this, amount);
    }

    public Display getDisplay() {
        return display;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public BankInterface getBankInterface() {
        return bank;
    }

    public CardProcessor getCardProcessor() {
        return cardProcessor;
    }

    public Keypad getKeypad() {
        return keypad;
    }

    public void transitionToState(ATMState nextState) {
        this.state = nextState;
    }

    public ATMState getCurrentState() {
        return state;
    }

    public DepositBox getDepositBox() {
        return depositBox;
    }
}
