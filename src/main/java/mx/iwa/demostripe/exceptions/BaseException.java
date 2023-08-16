package mx.iwa.demostripe.exceptions;

import lombok.Getter;
import mx.iwa.demostripe.enums.ErrorMessages;

/**
 * Generic {@link RuntimeException} used as base implementation for errors generated in the
 * application.
 */
public class BaseException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  @Getter private final String timestamp;

  @Getter private final ErrorMessages errorMessage;

  public BaseException(final String message, final ErrorMessages errorMessage) {
    this(ErrorCodeGenerator.generate(), message, errorMessage);
  }

  public BaseException(
      final String timestamp, final String message, final ErrorMessages errorMessage) {
    super(message);
    this.timestamp = timestamp;
    this.errorMessage = errorMessage;
  }
}
