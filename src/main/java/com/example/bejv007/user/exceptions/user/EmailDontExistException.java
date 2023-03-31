package com.example.bejv007.user.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Nenhum email encontrado")
public class EmailDontExistException extends Exception {
}
