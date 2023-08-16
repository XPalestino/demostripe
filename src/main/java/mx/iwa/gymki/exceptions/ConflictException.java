package mx.iwa.gymki.exceptions;

import mx.iwa.gymki.enums.ErrorMessages;

public class ConflictException extends BaseException {

  private static final long serialVersionUID = 1L;

  public ConflictException(final String message, final ErrorMessages errorMessage) {
    super(message, errorMessage);
  }
}
