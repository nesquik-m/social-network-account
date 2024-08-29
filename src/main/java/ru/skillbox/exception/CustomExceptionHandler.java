package ru.skillbox.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Void> notFound(AccountNotFoundException ex) {
        log.error("Ошибка при попытке получить аккаунт: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // TODO: нужно ли возвращать объект (body() ErrorResponse)?
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Void> alreadyExists(AlreadyExistsException ex) {
        log.error("Ошибка при создании аккаунта: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> badRequest(BadRequestException ex) {
        log.error("Ошибка при выполнении запроса: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> notValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errors = String.join("; ", errorMessages);
        log.error("Ошибка валидации входящих данных: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // TODO: нужно ли возвращать объект (body() ErrorResponse)?
    }

}
