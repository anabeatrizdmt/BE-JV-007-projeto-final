package com.example.bejv007.user.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "O email já está cadastrado")
public class EmailAlreadyExistsException extends Exception {
}
