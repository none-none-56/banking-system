package com.suhair.banking.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountRepository {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    // Spring uses this one
    @Autowired
    public AccountRepository() {
        accounts.put("acc1", new Account("acc1", new BigDecimal("1000.00")));
        accounts.put("acc2", new Account("acc2", new BigDecimal("5000.00")));
    }

    public AccountRepository(BigDecimal sourceBalance, BigDecimal destinationBalance) {
        accounts.put("acc1", new Account("acc1", sourceBalance));
        accounts.put("acc2", new Account("acc2", destinationBalance));
    }

    // Used by tests so they can set up the exact balances they assert on
    public AccountRepository(List<Account> seedAccounts) {
        seedAccounts.forEach(a -> accounts.put(a.getId(), a));
    }

    public Optional<Account> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public Collection<Account> findAll() {
        return List.copyOf(accounts.values());
    }
}