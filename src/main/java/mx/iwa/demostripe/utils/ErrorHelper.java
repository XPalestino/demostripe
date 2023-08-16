package mx.iwa.demostripe.utils;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.iwa.demostripe.exceptions.ErrorDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorHelper {
  public static List<ErrorDetail> buildErrorDetails(final List<ObjectError> errors) {
    return errors.stream().map(ErrorHelper::buildErrorDetail).collect(Collectors.toList());
  }

  public static ErrorDetail buildErrorDetail(final ObjectError error) {
    if (error instanceof FieldError) {
      final FieldError fieldError = (FieldError) error;

      return ErrorDetail.build(
          StringUtils.isNotBlank(error.getDefaultMessage())
              ? error.getDefaultMessage()
              : error.getCode(),
          fieldError.getField());
    }

    return ErrorDetail.build(error.getDefaultMessage(), error.getObjectName());
  }
}
