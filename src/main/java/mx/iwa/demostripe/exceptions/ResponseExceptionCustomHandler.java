package mx.iwa.demostripe.exceptions;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mx.iwa.demostripe.enums.ErrorMessages;
import mx.iwa.demostripe.utils.ErrorHelper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseExceptionCustomHandler {

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResource> handleConflictException(
      final ConflictException exception, final WebRequest request) {

    final ErrorResource errorResource =
        ErrorResource.build(exception.getErrorMessage(), exception.getTimestamp());

    log.error(
        "ConflictException in: {}, reason: {}, message: {}",
        getRequestDescription(request),
        errorResource,
        exception.getErrorMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResource);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public final ResponseEntity<ErrorResource> handlerNotFoundException(
      final NotFoundException exception, final WebRequest request) {

    final ErrorResource errorResource =
        ErrorResource.build(exception.getErrorMessage(), exception.getTimestamp());

    log.warn(
        "NotFoundException in: {}, reason: {}, message: {}",
        getRequestDescription(request),
        errorResource,
        exception.getErrorMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResource);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public final ResponseEntity<ErrorResource> handleDataIntegrityViolationException(
      final DataIntegrityViolationException exception, final WebRequest request) {

    final ErrorResource errorResource = ErrorResource.build(ErrorMessages.INTEGRITY_VIOLATION);

    log.error(
        "DataIntegrityViolationException in: {}, reason: {}",
        getRequestDescription(request),
        errorResource,
        exception);

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResource);
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public final ResponseEntity<ErrorResource> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException exception, final WebRequest request) {

    final ErrorMessages errorMessage = ErrorMessages.VALIDATION;
    final List<ErrorDetail> details = ErrorHelper.buildErrorDetails(exception.getAllErrors());

    log.warn("ValidationException in: {}, reason: {}", getRequestDescription(request), details);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResource.build(errorMessage, details));
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResource> handleException(
      final Exception exception, final WebRequest request) {

    final ErrorResource errorResource = ErrorResource.build(ErrorMessages.INTERNAL_SERVER_ERROR);

    log.warn(
        "Exception in: {}, reason: {}", getRequestDescription(request), errorResource, exception);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResource);
  }

  private String getRequestDescription(final WebRequest request) {
    return request.getDescription(false);
  }
}
