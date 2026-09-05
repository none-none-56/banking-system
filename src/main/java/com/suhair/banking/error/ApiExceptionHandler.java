package com.suhair.banking.error;

import com.suhair.banking.account.AccountNotFoundException;
import com.suhair.banking.account.InsufficientFundsException;
import com.suhair.banking.transfer.SameAccountTransferException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    public record ApiError(String code, String message) {}

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(AccountNotFoundException e) {
        return new ApiError("ACCOUNT_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError insufficientFunds(InsufficientFundsException e) {
        return new ApiError("INSUFFICIENT_FUNDS", e.getMessage());
    }

    @ExceptionHandler(SameAccountTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError sameAccountTransfer(SameAccountTransferException e) {
        return new ApiError("SAME_ACCOUNT", e.getMessage());
    }
}