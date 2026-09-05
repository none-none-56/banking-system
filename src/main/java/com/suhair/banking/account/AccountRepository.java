package com.suhair.banking.account;

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

    public AccountRepository() {
        accounts.put("acc1", new Account("acc1", new BigDecimal("1000.00")));
        accounts.put("acc2", new Account("acc2", new BigDecimal("500.00")));
    }

    public Optional<Account> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public Collection<Account> findAll() {
        return List.copyOf(accounts.values());
    }
}