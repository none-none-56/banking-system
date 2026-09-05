package com.suhair.banking.transfer;

import java.math.BigDecimal;

public record TransferRequest(String from, String to, BigDecimal amount) {}