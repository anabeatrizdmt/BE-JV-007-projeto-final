package com.example.bejv007.user.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Criptomoeda incompatível")
public class CurrencyNotSupportedException extends Exception{
}
