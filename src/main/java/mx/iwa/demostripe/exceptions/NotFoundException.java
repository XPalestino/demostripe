package mx.iwa.demostripe.exceptions;

import mx.iwa.demostripe.enums.ErrorMessages;

public class NotFoundException extends BaseException {

  private static final long serialVersionUID = 1L;

  public NotFoundException(final String message, final ErrorMessages errorMessage) {
    super(message, errorMessage);
  }
}
