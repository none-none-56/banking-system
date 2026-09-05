package com.suhair.banking;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TransferController {

    private Map<String, BigDecimal> Accounts;

    public TransferController(Map<String, BigDecimal> accounts) {
        this.Accounts = accounts;
        this.Accounts.put("acc1", BigDecimal.valueOf(1000.00));
        this.Accounts.put("acc2", BigDecimal.valueOf(5000.00));
    }

    @GetMapping("/accounts")
    public String list_accounts() {
        return this.Accounts.toString();
    }

//    public Map<String, BigDecimal> getAccounts() {
//        return Accounts;
//    }
//
//    public void setAccounts(Map<String, BigDecimal> accounts) {
//        Accounts = accounts;
//    }

    public record TransferRequest(String from, String to, BigDecimal amount) {};

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest transfer) {
        if (Objects.equals(transfer.from, "acc1") && Objects.equals(transfer.to, "acc2")) {
            if ((transfer.amount.compareTo(BigDecimal.ZERO) >= 0) && (transfer.amount.compareTo(Accounts.get("acc1")) <= 0)) {
                BigDecimal acc1_amount = this.Accounts.get("acc1").subtract(transfer.amount);
                BigDecimal acc2_amount = this.Accounts.get("acc2").add(transfer.amount);
                this.Accounts.put("acc1", acc1_amount);
                this.Accounts.put("acc2", acc2_amount);

                return "Success";
            }
        } else if (Objects.equals(transfer.from, "acc2") && Objects.equals(transfer.to, "acc1")) {
            if ((transfer.amount.compareTo(BigDecimal.ZERO) >= 0) && (transfer.amount.compareTo(Accounts.get("acc2")) <= 0)) {
                BigDecimal acc1_amount = this.Accounts.get("acc2").subtract(transfer.amount);
                BigDecimal acc2_amount = this.Accounts.get("acc1").add(transfer.amount);
                this.Accounts.put("acc2", acc2_amount);
                this.Accounts.put("acc1", acc1_amount);

                return "Success";
            }
        }
        return "Failed";
    }
}
