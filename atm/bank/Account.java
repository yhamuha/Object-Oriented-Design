package atm.bank;

import atm.bank.enums.AccountType;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Account {
    private static byte[] calculateMd5(String pinNumber) {
        try {
            return MessageDigest.getInstance("MD5").digest(pinNumber.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographicException("MD5 algorithm not available in the current environment", e);
        }
    }
    private BigDecimal balance;
    private final String accountNumber;
    private final String cardNumber;
    private final byte[] cardPinHash;
    private final AccountType accountType;

    public Account(final String accountNumber,
                   final AccountType type,
                   final String cardNumber,
                   final String pin) {
        this.accountNumber = accountNumber;
        this.accountType = type;
        this.cardNumber = cardNumber;
        this.cardPinHash = calculateMd5(pin);
        this.balance = BigDecimal.ZERO;
    }

    public boolean validatePin(String pinNumber) {
        byte[] entryPinHash = calculateMd5(pinNumber);
        return Arrays.equals(cardPinHash, entryPinHash);
    }

    public void updateBalanceWithTransaction(final BigDecimal balanceChange) {
        this.balance = this.balance.add(balanceChange);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }


}
