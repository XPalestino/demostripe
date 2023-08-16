package mx.iwa.gymki.utils;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.iwa.gymki.enums.ErrorMessages;
import mx.iwa.gymki.exceptions.NotFoundException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidateHelper {

  public static <T> T checkNull(
      final Optional<T> object,
      final Class<?> objectType,
      final Object value,
      final ErrorMessages errorMessages) {
    return object.orElseThrow(
        () ->
            new NotFoundException(
                "Not found: " + value + objectType.getSimpleName(), errorMessages));
  }
}
