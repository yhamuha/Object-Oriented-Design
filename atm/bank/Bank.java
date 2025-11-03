package atm.bank;

import atm.bank.enums.AccountType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank implements BankInterface {
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Account> accountByCard = new HashMap<>();

    @Override
    public void addAccount(final String accountNumber,
                           final AccountType type,
                           final String cardNumber,
                           final String pin) {
        final Account newAccount = new Account(accountNumber, type, cardNumber, pin);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        accountByCard.put(newAccount.getCardNumber(), newAccount);
    }

    @Override
    public boolean validateCard(final String cardNumber) {
        return getAccountByCard(cardNumber) != null;
    }

    @Override
    public boolean checkPin(String cardNumber, String pinNumber) {
        Account account = getAccountByCard(cardNumber);
        if (account != null) {
            return account.validatePin(pinNumber);
        }
        return false;
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public Account getAccountByCard(String cardNumber) {
        return accountByCard.get(cardNumber);
    }

    @Override
    public boolean withdrawFunds(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) >= 0) {
            account.updateBalanceWithTransaction(amount.negate());
            return true;
        }
        return false;
    }
}
