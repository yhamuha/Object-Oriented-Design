package atm;

import atm.bank.Bank;
import atm.bank.enums.AccountType;
import atm.bank.enums.TransactionType;
import atm.hardware.input.SimulatedCardProcessor;
import atm.hardware.input.SimulatedDepositBox;
import atm.hardware.input.SimulatedKeyPad;
import atm.hardware.output.ConsoleDisplay;
import atm.hardware.output.SimulatedCashDispenser;
import atm.states.IdleState;
import atm.states.PinEntryState;
import atm.states.TransactionSelectionState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMTest {
    @Test
    public void testEndToEndDeposit() {
        System.out.println("\n=== Testing End-to-End Deposit Flow ===");

        Bank bank = new Bank();
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234");
        System.out.println("✓ Bank initialized with account 123456 (PIN: 1234)");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized with all components");

        assertEquals(IdleState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM is in IDLE state (ready to accept cards)");

        System.out.println("\n--- Step 1: Inserting Card ---");
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine);
        assertEquals("Please enter your PIN", display.getDisplayedMessage());
        System.out.println("✓ Card inserted successfully");
        System.out.println("✓ ATM displays: '" + display.getDisplayedMessage() + "'");

        System.out.println("\n--- Step 2: Entering PIN ---");
        keypad.handlePinEntry("1234", atmMachine);
        assertEquals("PIN correct, select transaction type", display.getDisplayedMessage());
        System.out.println("✓ PIN entered: 1234");
        System.out.println("✓ ATM displays: '" + display.getDisplayedMessage() + "'");

        System.out.println("\n--- Step 3: Selecting Deposit Transaction ---");
        keypad.handleSelectTransaction(TransactionType.DEPOSIT, atmMachine);
        assertEquals("Please deposit cash into the deposit box.", display.getDisplayedMessage());
        System.out.println("✓ Deposit transaction selected");
        System.out.println("✓ ATM displays: '" + display.getDisplayedMessage() + "'");

        System.out.println("\n--- Step 4: Depositing Cash ---");
        depositBox.acceptDeposit(new BigDecimal("200"), atmMachine);
        assertEquals("Deposit successful. Deposited amount: 200 to account: 123456", display.getDisplayedMessage());
        System.out.println("✓ Cash deposited: $200");
        System.out.println("✓ ATM displays: '" + display.getDisplayedMessage() + "'");

        assertEquals(TransactionSelectionState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM returned to transaction selection state");

        assertEquals(new BigDecimal("200"), bank.getAccountByAccountNumber("123456").getBalance());
        System.out.println("✓ Account balance verified: $200");
        System.out.println("=== End-to-End Deposit Test Completed Successfully ===\n");
    }

    @Test
    public void testMultipleDeposits() {
        System.out.println("\n=== Testing Multiple Deposits ===");

        Bank bank = new Bank();
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234");
        System.out.println("✓ Bank initialized with account 123456 (PIN: 1234)");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized");

        assertEquals(IdleState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM is in IDLE state");

        System.out.println("\n--- First Deposit Session ---");
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine);
        assertEquals("Please enter your PIN", display.getDisplayedMessage());
        System.out.println("✓ Card inserted, PIN requested");

        keypad.handlePinEntry("1234", atmMachine);
        assertEquals("PIN correct, select transaction type", display.getDisplayedMessage());
        System.out.println("✓ PIN verified, transaction menu displayed");

        keypad.handleSelectTransaction(TransactionType.DEPOSIT, atmMachine);
        assertEquals("Please deposit cash into the deposit box.", display.getDisplayedMessage());
        System.out.println("✓ Deposit selected, waiting for cash");

        depositBox.acceptDeposit(new BigDecimal("200"), atmMachine);
        assertEquals("Deposit successful. Deposited amount: 200 to account: 123456", display.getDisplayedMessage());
        System.out.println("✓ First deposit: $200 completed");

        assertEquals(new BigDecimal("200"), bank.getAccountByAccountNumber("123456").getBalance());
        System.out.println("✓ Account balance after first deposit: $200");

        System.out.println("\n--- Second Deposit Session ---");
        keypad.handleSelectTransaction(TransactionType.DEPOSIT, atmMachine);
        assertEquals("Please deposit cash into the deposit box.", display.getDisplayedMessage());
        System.out.println("✓ Second deposit selected, waiting for cash");

        depositBox.acceptDeposit(new BigDecimal("300"), atmMachine);
        assertEquals("Deposit successful. Deposited amount: 300 to account: 123456", display.getDisplayedMessage());
        System.out.println("✓ Second deposit: $300 completed");

        assertEquals(new BigDecimal("500"), bank.getAccountByAccountNumber("123456").getBalance());
        System.out.println("✓ Final account balance: $500 (200 + 300)");
        System.out.println("=== Multiple Deposits Test Completed Successfully ===\n");
    }

    @Test
    public void testMultipleWithdrawals() {
        System.out.println("\n=== Testing Multiple Withdrawals ===");

        Bank bank = new Bank();
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234");
        bank.getAccountByAccountNumber("123456").updateBalanceWithTransaction(new BigDecimal("500"));
        System.out.println("✓ Bank initialized with account 123456 (PIN: 1234)");
        System.out.println("✓ Account pre-loaded with $500");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized");

        assertEquals(IdleState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM is in IDLE state");

        System.out.println("\n--- First Withdrawal Session ---");
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine);
        assertEquals("Please enter your PIN", display.getDisplayedMessage());
        System.out.println("✓ Card inserted, PIN requested");

        keypad.handlePinEntry("1234", atmMachine);
        assertEquals("PIN correct, select transaction type", display.getDisplayedMessage());
        System.out.println("✓ PIN verified, transaction menu displayed");

        keypad.handleSelectTransaction(TransactionType.WITHDRAW, atmMachine);
        assertEquals("Enter amount to withdraw:", display.getDisplayedMessage());
        System.out.println("✓ Withdrawal selected, amount requested");

        keypad.handleAmountEntry(new BigDecimal("200"), atmMachine);
        assertEquals("Please take your cash.", display.getDisplayedMessage());
        System.out.println("✓ First withdrawal: $200 dispensed");

        assertEquals(new BigDecimal("300"), bank.getAccountByAccountNumber("123456").getBalance());
        System.out.println("✓ Account balance after first withdrawal: $300 (500 - 200)");

        System.out.println("\n--- Second Withdrawal Session ---");
        keypad.handleSelectTransaction(TransactionType.WITHDRAW, atmMachine);
        assertEquals("Enter amount to withdraw:", display.getDisplayedMessage());
        System.out.println("✓ Second withdrawal selected, amount requested");

        keypad.handleAmountEntry(new BigDecimal("100"), atmMachine);
        assertEquals("Please take your cash.", display.getDisplayedMessage());
        System.out.println("✓ Second withdrawal: $100 dispensed");

        assertEquals(new BigDecimal("200"), bank.getAccountByAccountNumber("123456").getBalance());
        System.out.println("✓ Account balance after second withdrawal: $200 (300 - 100)");

        System.out.println("\n--- Attempting Excessive Withdrawal ---");
        keypad.handleSelectTransaction(TransactionType.WITHDRAW, atmMachine);
        assertEquals("Enter amount to withdraw:", display.getDisplayedMessage());
        keypad.handleAmountEntry(new BigDecimal("300"), atmMachine);
        assertEquals("Insufficient funds, please try again.", display.getDisplayedMessage());
        System.out.println("✓ Attempted to withdraw $300 (exceeds balance of $200)");
        System.out.println("✓ ATM correctly rejected: 'Insufficient funds'");

        assertEquals(new BigDecimal("200"), bank.getAccountByAccountNumber("123456").getBalance());
        System.out.println("✓ Account balance unchanged: $200 (no funds deducted)");
        System.out.println("=== Multiple Withdrawals Test Completed Successfully ===\n");
    }

    @Test
    public void testInvalidCardInsertion() {
        System.out.println("\n=== Testing Invalid Card Insertion ===");

        Bank bank = new Bank();
        System.out.println("✓ Bank initialized (no accounts registered)");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized");

        assertEquals(IdleState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM is in IDLE state");

        System.out.println("\n--- Attempting Invalid Card Insertion ---");
        cardProcessor.handleCardInsertion("9999-8888-7777-6666", atmMachine);
        assertEquals("Invalid card. Please try again.", display.getDisplayedMessage());
        System.out.println("✓ Invalid card inserted: 9999-8888-7777-6666");
        System.out.println("✓ ATM correctly rejected: 'Invalid card. Please try again.'");

        assertEquals(IdleState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM remains in IDLE state (ready for valid card)");
        System.out.println("=== Invalid Card Test Completed Successfully ===\n");
    }

    @Test
    public void testInvalidPinEntry() {
        System.out.println("\n=== Testing Invalid PIN Entry ===");

        Bank bank = new Bank();
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234");
        System.out.println("✓ Bank initialized with account 123456 (PIN: 1234)");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized");

        System.out.println("\n--- Inserting Valid Card ---");
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine);
        assertEquals("Please enter your PIN", display.getDisplayedMessage());
        System.out.println("✓ Valid card inserted: 1111-2222-3333-4444");
        System.out.println("✓ ATM requests PIN entry");

        System.out.println("\n--- Entering Invalid PIN ---");
        keypad.handlePinEntry("0000", atmMachine);
        assertEquals("Invalid PIN. Please try again", display.getDisplayedMessage());
        System.out.println("✓ Invalid PIN entered: 0000 (correct PIN is 1234)");
        System.out.println("✓ ATM correctly rejected: 'Invalid PIN. Please try again'");

        assertEquals(PinEntryState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM remains in PIN entry state (allows retry)");
        System.out.println("=== Invalid PIN Test Completed Successfully ===\n");
    }

    @Test
    public void testWithdrawWithoutSufficientFunds() {
        System.out.println("\n=== Testing Withdrawal Without Sufficient Funds ===");

        Bank bank = new Bank();
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234");
        bank.getAccountByAccountNumber("123456").updateBalanceWithTransaction(new BigDecimal("100"));
        System.out.println("✓ Bank initialized with account 123456 (PIN: 1234)");
        System.out.println("✓ Account balance: $100");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized");

        System.out.println("\n--- Starting Withdrawal Process ---");
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine);
        assertEquals("Please enter your PIN", display.getDisplayedMessage());
        System.out.println("✓ Card inserted, PIN requested");

        keypad.handlePinEntry("1234", atmMachine);
        assertEquals("PIN correct, select transaction type", display.getDisplayedMessage());
        System.out.println("✓ PIN verified, transaction menu displayed");

        keypad.handleSelectTransaction(TransactionType.WITHDRAW, atmMachine);
        assertEquals("Enter amount to withdraw:", display.getDisplayedMessage());
        System.out.println("✓ Withdrawal selected, amount requested");

        System.out.println("\n--- Attempting Excessive Withdrawal ---");
        keypad.handleAmountEntry(new BigDecimal("200"), atmMachine);
        assertEquals("Insufficient funds, please try again.", display.getDisplayedMessage());
        System.out.println("✓ Attempted to withdraw $200 (exceeds balance of $100)");
        System.out.println("✓ ATM correctly rejected: 'Insufficient funds, please try again.'");

        assertEquals(TransactionSelectionState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM returned to transaction selection state (allows other transactions)");
        System.out.println("=== Insufficient Funds Test Completed Successfully ===\n");
    }

    @Test
    public void testEjectCardDuringTransaction() {
        System.out.println("\n=== Testing Card Ejection During Transaction ===");

        Bank bank = new Bank();
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234");
        System.out.println("✓ Bank initialized with account 123456 (PIN: 1234)");

        SimulatedCardProcessor cardProcessor = new SimulatedCardProcessor();
        SimulatedDepositBox depositBox = new SimulatedDepositBox();
        ConsoleDisplay display = new ConsoleDisplay();
        SimulatedKeyPad keypad = new SimulatedKeyPad();
        SimulatedCashDispenser cashDispenser = new SimulatedCashDispenser();

        ATMMachine atmMachine = new ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display);
        System.out.println("✓ ATM machine initialized");

        System.out.println("\n--- Starting Transaction Process ---");
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine);
        assertEquals("Please enter your PIN", display.getDisplayedMessage());
        System.out.println("✓ Card inserted, PIN requested");

        keypad.handlePinEntry("1234", atmMachine);
        assertEquals("PIN correct, select transaction type", display.getDisplayedMessage());
        System.out.println("✓ PIN verified, transaction menu displayed");

        keypad.handleSelectTransaction(TransactionType.DEPOSIT, atmMachine);
        assertEquals("Please deposit cash into the deposit box.", display.getDisplayedMessage());
        System.out.println("✓ Deposit selected, waiting for cash");

        System.out.println("\n--- Ejecting Card During Transaction ---");
        cardProcessor.handleCardEjection(atmMachine);
        assertEquals("Transaction cancelled, card ejected", display.getDisplayedMessage());
        System.out.println("✓ Card ejected during active transaction");
        System.out.println("✓ ATM displays: 'Transaction cancelled, card ejected'");

        assertEquals(IdleState.class, atmMachine.getCurrentState().getClass());
        System.out.println("✓ ATM returned to IDLE state (ready for new card)");
        System.out.println("=== Card Ejection Test Completed Successfully ===\n");
    }
}
