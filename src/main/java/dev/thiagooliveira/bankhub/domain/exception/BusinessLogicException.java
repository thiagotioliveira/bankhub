package dev.thiagooliveira.bankhub.domain.exception;

public class BusinessLogicException extends RuntimeException {
  public BusinessLogicException(String message) {
    super(message);
  }
}
