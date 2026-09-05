package com.suhair.banking.account;

import java.math.BigDecimal;

public class Account {
    private final String id;
    private BigDecimal balance;

    public Account(String id, BigDecimal initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() { return id; }
    public BigDecimal getBalance() { return balance; }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(id);
        }
        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}