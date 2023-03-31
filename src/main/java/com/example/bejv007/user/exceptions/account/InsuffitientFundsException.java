package com.example.bejv007.user.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Saldo insuficiente")
public class InsuffitientFundsException extends Exception {
}
