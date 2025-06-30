package dev.thiagooliveira.bankhub.infra.http;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.spec.http.dto.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessLogicException.class)
  public ResponseEntity<Error> handleBusinessLogicException(BusinessLogicException ex) {
    int statusCode = ex.errorCode();
    return ResponseEntity.status(statusCode)
        .body(new Error().code(statusCode).message(ex.getMessage()));
  }
}
