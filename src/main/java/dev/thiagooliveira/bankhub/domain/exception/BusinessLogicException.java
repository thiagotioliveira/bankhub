package dev.thiagooliveira.bankhub.domain.exception;

public class BusinessLogicException extends RuntimeException {
  private final BusinessLogicCodeError errorCode;

  public BusinessLogicException(String message) {
    super(message);
    this.errorCode = BusinessLogicCodeError.GENERIC_ERROR;
  }

  private BusinessLogicException(BusinessLogicCodeError errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public static BusinessLogicException notFound(String message) {
    return new BusinessLogicException(BusinessLogicCodeError.NOT_FOUND, message);
  }

  public static BusinessLogicException badRequest(String message) {
    return new BusinessLogicException(BusinessLogicCodeError.GENERIC_ERROR, message);
  }

  public int errorCode() {
    return this.errorCode.code;
  }

  public enum BusinessLogicCodeError {
    GENERIC_ERROR(400),
    NOT_FOUND(404);

    private final int code;

    private BusinessLogicCodeError(int code) {
      this.code = code;
    }
  }
}
