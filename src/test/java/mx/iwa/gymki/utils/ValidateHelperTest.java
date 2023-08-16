package mx.iwa.gymki.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.Optional;
import mx.iwa.gymki.enums.ErrorMessages;
import mx.iwa.gymki.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

public class ValidateHelperTest {

  @Test
  void checkNull_should_not_throw_exception_when_object_is_not_empty() {
    final String value = "value";
    final Optional<String> nonEmptyObject = Optional.of(value);
    final Class<?> objectType = String.class;

    assertThatCode(
            () ->
                ValidateHelper.checkNull(nonEmptyObject, objectType, value, ErrorMessages.USER_000))
        .doesNotThrowAnyException();
  }

  @Test
  void checkNull_should_throw_exception_when_object_is_empty() {

    final Optional<Object> emptyObject = Optional.empty();
    final Class<?> objectType = String.class;
    final Object value = "value";

    assertThatThrownBy(
            () -> ValidateHelper.checkNull(emptyObject, objectType, value, ErrorMessages.USER_000))
        .isInstanceOf(NotFoundException.class);
  }
}
