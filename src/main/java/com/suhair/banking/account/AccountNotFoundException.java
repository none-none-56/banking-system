package com.suhair.banking.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountId) {

        super("Account " + accountId + " doesn't exist");
    }
}
