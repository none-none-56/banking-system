package com.suhair.banking.transfer;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank String from,
        @NotBlank String to,
        @NotNull @Positive @Digits(integer = 15, fraction = 2) BigDecimal amount) {
}