package com.suhair.banking.account;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String accountId) {

        super("Insufficient funds in account " + accountId);
    }
}
