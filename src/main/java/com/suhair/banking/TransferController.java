package com.suhair.banking;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TransferController {

    private Map<String, Double> Accounts;

    public TransferController(Map<String, Double> accounts) {
        this.Accounts = accounts;
        this.Accounts.put("acc1", 1000.0);
        this.Accounts.put("acc2", 5000.0);
    }

    @GetMapping("/accounts")
    public String list_accounts() {
        return this.Accounts.toString();
    }

//    public Map<String, Double> getAccounts() {
//        return Accounts;
//    }
//
//    public void setAccounts(Map<String, Double> accounts) {
//        Accounts = accounts;
//    }

    public record TransferRequest(String from, String to, Double amount) {};

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest transfer) {
        if (Objects.equals(transfer.from, "acc1") && Objects.equals(transfer.to, "acc2")) {
            if ((transfer.amount >= 0.0) && (transfer.amount <= this.Accounts.get("acc1"))) {
                Double acc1_amount = this.Accounts.get("acc1") - transfer.amount;
                Double acc2_amount = this.Accounts.get("acc2") + transfer.amount;
                this.Accounts.put("acc1", acc1_amount);
                this.Accounts.put("acc2", acc2_amount);

                return "Success";
            }
        } else if (Objects.equals(transfer.from, "acc2") && Objects.equals(transfer.to, "acc1")) {
            if ((transfer.amount >= 0.0) && (transfer.amount <= this.Accounts.get("acc5"))) {
                Double acc2_amount = this.Accounts.get("acc2") - transfer.amount;
                Double acc1_amount = this.Accounts.get("acc1") + transfer.amount;
                this.Accounts.put("acc2", acc2_amount);
                this.Accounts.put("acc1", acc1_amount);

                return "Success";
            }
        }
        return "Failed";
    }
}
