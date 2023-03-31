package com.example.bejv007.user.exceptions.account;

import com.example.bejv007.user.exceptions.user.EmailAlreadyExistsException;
import com.example.bejv007.user.request.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountExceptionsHandler {
    @ExceptionHandler(InsuffitientFundsException.class)
    public ResponseEntity<ErrorMessage> handleInsuffitientFundsException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("Saldo insuficiente", HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CurrencyNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleCurrencyNotSupportedException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("Criptomoeda incompatível", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
