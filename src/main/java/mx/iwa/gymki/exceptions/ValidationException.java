package mx.iwa.gymki.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final BindingResult error;
}
