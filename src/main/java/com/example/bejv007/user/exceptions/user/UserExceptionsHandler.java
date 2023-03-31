package com.example.bejv007.user.exceptions.user;

import com.example.bejv007.user.request.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionsHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleEmailAlreadyExistsException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("O Email já está cadastrado", HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUsernameAlreadyExistsException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("Já existe um username com esse nome", HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleIdNotFoundException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("O ID informado não foi encontrado na nossa base de dados.", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailDontExistException.class)
    public ResponseEntity<ErrorMessage> handleEmailDontExistException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("Nenhum email encontrado.", HttpStatus.OK.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(Exception ex) {
        ErrorMessage errorResponse = new ErrorMessage("Usuário não encontrado", HttpStatus.OK.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
