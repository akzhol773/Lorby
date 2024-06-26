package org.neobis.neoauthproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.neobis.neoauthproject.dto.ExceptionDto;
import org.neobis.neoauthproject.dto.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerErrorException;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<Object> handleUserRoleNotFoundException(
            UserRoleNotFoundException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> handlePasswordNotMatchException(PasswordNotMatchException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Object> handleTokenNotFoundException(
            TokenNotFoundException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyConfirmedException.class)
    public ResponseEntity<Object> handleEmailAlreadyConfirmedException(
            EmailAlreadyConfirmedException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(
            TokenExpiredException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<?> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleDisabledException(
            DisabledException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(
            InvalidTokenException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(
            UsernameNotFoundException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Object> handleServerErrorException(
            ServerErrorException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PasswordDontMatchException.class)
    public ResponseEntity<?> handlePasswordDontMatchException(PasswordDontMatchException ex) {
        UUID exceptionUUID = UUID.randomUUID();
        log.error("Exception UUID: {}", exceptionUUID, ex);
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

}
