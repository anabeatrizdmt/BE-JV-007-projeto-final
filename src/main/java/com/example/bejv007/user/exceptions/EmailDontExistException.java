package com.example.bejv007.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = " o email não existe")
public class EmailDontExistException extends Exception {

}
