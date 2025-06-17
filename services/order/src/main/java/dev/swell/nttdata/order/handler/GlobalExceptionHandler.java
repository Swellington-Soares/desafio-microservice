package dev.swell.nttdata.order.handler;

import dev.swell.nttdata.order.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleException(BusinessException ex) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
