package mx.iwa.gymki.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import mx.iwa.gymki.exceptions.ErrorDetail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ErrorHelperTest {

  static Stream<Arguments> buildErrorMessage_data_provider() {
    return Stream.of(
        Arguments.of(
            "Invalid field name",
            buildFieldError("must not be blank"),
            ErrorDetail.build("must not be blank", "fieldName")),
        Arguments.of(
            "Default message is blank", buildFieldError(""), ErrorDetail.build(null, "fieldName")),
        Arguments.of(
            "Invalid object",
            buildObjectError(),
            ErrorDetail.build("Default Message", "objectName")));
  }

  @ParameterizedTest
  @MethodSource("buildErrorMessage_data_provider")
  void buildErrorMessage_should_return_expected_results(
      final String testCase, final ObjectError error, final ErrorDetail expectedResult) {
    final ErrorDetail errorMessage = ErrorHelper.buildErrorDetail(error);

    assertThat(errorMessage.getTarget()).isEqualTo(expectedResult.getTarget());
    assertThat(errorMessage.getMessage()).isEqualToIgnoringWhitespace(expectedResult.getMessage());
  }

  private static ObjectError buildFieldError(final String defaultMessage) {
    return new FieldError("objectName", "fieldName", null, false, null, null, defaultMessage);
  }

  private static ObjectError buildObjectError() {
    return new ObjectError("objectName", "Default Message");
  }
}
