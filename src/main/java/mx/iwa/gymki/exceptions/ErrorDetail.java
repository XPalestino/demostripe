package mx.iwa.gymki.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDetail {

  private final String message;
  private final String target;

  public static ErrorDetail build(final String message, final String target) {
    return ErrorDetail.builder().message(message).target(target).build();
  }
}
