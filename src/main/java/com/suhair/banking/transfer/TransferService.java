package com.suhair.banking.transfer;

import com.suhair.banking.account.Account;
import com.suhair.banking.account.AccountNotFoundException;
import com.suhair.banking.account.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final AccountRepository accounts;

    public TransferService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    public void transfer(String fromId, String toId, BigDecimal amount) {
        if (fromId.equals(toId)) throw new SameAccountTransferException(toId);

        Account from = accounts.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException(fromId));
        Account to = accounts.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException(toId));

        from.withdraw(amount);
        to.deposit(amount);
    }
}