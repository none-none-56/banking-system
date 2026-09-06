package com.suhair.banking.transfer;

import com.suhair.banking.account.AccountRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TransferServiceConcurrencyTest {

    @Test
    void concurrentTransfersDoNotCreateOrDestroyMoney() throws InterruptedException {
        AccountRepository accounts = new AccountRepository(
                new BigDecimal("1000.00"), new BigDecimal("1000.00"));
        TransferService service = new TransferService(accounts);

        BigDecimal startingTotal = new BigDecimal("2000.00");
        int transfers = 1000;

        ExecutorService pool = Executors.newFixedThreadPool(10);
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < transfers; i++) {
            boolean forward = (i % 2 == 0);
            pool.submit(() -> {
                start.await();                       // all threads wait here
                if (forward) {
                    service.transfer("acc1", "acc2", new BigDecimal("1.00"));
                } else {
                    service.transfer("acc2", "acc1", new BigDecimal("1.00"));
                }
                return null;
            });
        }

        start.countDown();                           // release them all at once
        pool.shutdown();
        pool.awaitTermination(30, TimeUnit.SECONDS);

        BigDecimal acc1 = accounts.findById("acc1").orElseThrow().getBalance();
        BigDecimal acc2 = accounts.findById("acc2").orElseThrow().getBalance();

        assertThat(acc1.add(acc2)).isEqualByComparingTo(startingTotal);
        assertThat(acc1).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(acc2).isGreaterThanOrEqualTo(BigDecimal.ZERO);
    }
}