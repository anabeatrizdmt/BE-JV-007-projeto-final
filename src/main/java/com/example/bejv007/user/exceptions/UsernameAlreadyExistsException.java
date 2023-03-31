package com.example.bejv007.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Já existe um username com esse nome")
public class UsernameAlreadyExistsException extends Exception{
}
