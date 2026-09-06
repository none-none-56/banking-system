package com.suhair.banking.transfer;

import com.suhair.banking.account.Account;
import com.suhair.banking.account.AccountNotFoundException;
import com.suhair.banking.account.AccountRepository;
import com.suhair.banking.account.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest {
    private AccountRepository accounts;
    private TransferService service;

    @BeforeEach
    void setUp() {
        accounts = new AccountRepository(List.of(
                new Account("acc1", new BigDecimal("1000.00")),
                new Account("acc2", new BigDecimal("5000.00"))));
        service = new TransferService(accounts);
    }

    // From acc1 to acc2
    @Test
    void transferMovesMoneyBetweenAccounts() {
        service.transfer("acc1", "acc2", new BigDecimal("100.00"));

        assertThat(accounts.findById("acc1").orElseThrow().getBalance())
                .isEqualByComparingTo("900.00");
        assertThat(accounts.findById("acc2").orElseThrow().getBalance())
                .isEqualByComparingTo("5100.00");
    }

    // From acc2 to acc1
    @Test
    void transferMovesMoneyInReverseDirection() {
        service.transfer("acc2", "acc1", new BigDecimal("100.00"));

        assertThat(accounts.findById("acc1").orElseThrow().getBalance())
                .isEqualByComparingTo("1100.00");
        assertThat(accounts.findById("acc2").orElseThrow().getBalance())
                .isEqualByComparingTo("4900.00");
    }

    @Test
    void transferFailsWhenBalanceIsTooLowAndLeavesBalancesUnchanged() {
        assertThrows(InsufficientFundsException.class,
                () -> service.transfer("acc1", "acc2", new BigDecimal("1000.01")));

        assertThat(accounts.findById("acc1").orElseThrow().getBalance())
                .isEqualByComparingTo("1000.00");
        assertThat(accounts.findById("acc2").orElseThrow().getBalance())
                .isEqualByComparingTo("5000.00");
    }

    @Test
    void transferFailsWhenSourceAccountIdDoesNotExist() {
        assertThrows(AccountNotFoundException.class,
                () -> service.transfer("UNKNOWN_ACCOUNT", "acc2", new BigDecimal("100.0")));
    }

    @Test
    void transferFailsWhenDestinationAccountIdDoesNotExist() {
        assertThrows(AccountNotFoundException.class,
                () -> service.transfer("acc1", "UNKNOWN_ACCOUNT", new BigDecimal("100.0")));
    }

    @Test
    void transferFailsWhenSourceAccountEqualsDestinationAccount() {
        assertThrows(SameAccountTransferException.class,
                () -> service.transfer("acc1", "acc1", new BigDecimal("100.0")));
    }

    @Test
    void transferFullAccountBalanceSucceedsAndBalanceIsZero() {
        service.transfer("acc1", "acc2", new BigDecimal("1000.00"));

        assertThat(accounts.findById("acc1").orElseThrow().getBalance())
                .isEqualByComparingTo("0.00");
        assertThat(accounts.findById("acc2").orElseThrow().getBalance())
                .isEqualByComparingTo("6000.00");
    }
}