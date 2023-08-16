package mx.iwa.demostripe.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import mx.iwa.demostripe.enums.ErrorMessages;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ErrorResource {
  @NotBlank @Builder.Default private final String timestamp = ErrorCodeGenerator.generate();

  @NotBlank private final String message;

  private final String code;

  private final List<ErrorDetail> details;

  public static ErrorResource build(final ErrorMessages errorMessage, final String timestamp) {
    return ErrorResource.builder()
        .code(errorMessage.name())
        .message(errorMessage.getDescription())
        .timestamp(timestamp)
        .build();
  }

  public static ErrorResource build(final ErrorMessages errorMessage) {
    return ErrorResource.builder()
        .code(errorMessage.name())
        .message(errorMessage.getDescription())
        .build();
  }

  public static ErrorResource build(
      final ErrorMessages errorMessage, final List<ErrorDetail> details) {
    return ErrorResource.builder()
        .code(errorMessage.name())
        .message(errorMessage.getDescription())
        .details(details)
        .build();
  }
}
