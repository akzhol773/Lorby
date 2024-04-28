package org.neobis.neoauthproject.exception;

import org.neobis.neoauthproject.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<Object> handleUserRoleNotFoundException(
            UserRoleNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> handlePasswordNotMatchException(PasswordNotMatchException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Object> handleTokenNotFoundException(
            TokenNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyConfirmedException.class)
    public ResponseEntity<Object> handleEmailAlreadyConfirmedException(
            EmailAlreadyConfirmedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(
            TokenExpiredException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<?> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }



}
