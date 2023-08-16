package mx.iwa.gymki.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.stream.Stream;
import mx.iwa.gymki.BaseRepositoryTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends BaseRepositoryTest {
  private static final String DBUNIT_XML = "classpath:dbunit/repository/users.xml";

  @Autowired private UserRepository userRepository;

  static Stream<Arguments> existsByUsernameAndIdNot_data_provider() {
    return Stream.of(
        Arguments.of("Match username and Id", "GARELLANO", 1, false),
        Arguments.of("Existing username and ID no match", "TEST", 1, true),
        Arguments.of("Existing username and non-existing id", "TEST", 999, true),
        Arguments.of("Non-existing user", "FAKE", 999, false));
  }

  @DatabaseSetup(DBUNIT_XML)
  @ParameterizedTest
  @MethodSource("existsByUsernameAndIdNot_data_provider")
  void existsByUsernameAndIdNot_should_return_expected_results(
      final String testCase,
      final String username,
      final int userId,
      final boolean expectedResult) {
    // GIVEN existsByUsernameAndIdNot_data_provider

    // WHEN
    final boolean result = userRepository.existsByUsernameAndIdNot(username, userId);

    // THEN
    assertThat(result).isEqualTo(expectedResult);
  }

  static Stream<Arguments> existsByEmailAndIdNot_data_provider() {
    return Stream.of(
        Arguments.of("Match email and Id", "test@iwa.com.mx", 1, false),
        Arguments.of("Existing email and ID no match", "test2@iwa.com.mx", 1, true),
        Arguments.of("Existing email and non-existing id", "test2@iwa.com.mx", 999, true),
        Arguments.of("Non-existing user", "fake@iwa.com.mx", 999, false));
  }

  @DatabaseSetup(DBUNIT_XML)
  @ParameterizedTest
  @MethodSource("existsByEmailAndIdNot_data_provider")
  void existsByEmailAndIdNot_should_return_expected_results(
      final String testCase, final String email, final int userId, final boolean expectedResult) {
    // GIVEN existsByEmailAndIdNot_data_provider

    // WHEN
    final boolean result = userRepository.existsByEmailAndIdNot(email, userId);

    // THEN
    assertThat(result).isEqualTo(expectedResult);
  }

  static Stream<Arguments> existsByEmail_data_provider() {
    return Stream.of(
        Arguments.of("Existing email", "test2@iwa.com.mx", true),
        Arguments.of("Non-existing user", "fake@iwa.com.mx", false));
  }

  @DatabaseSetup(DBUNIT_XML)
  @ParameterizedTest
  @MethodSource("existsByEmail_data_provider")
  void existsByEmail_should_return_expected_results(
      final String testCase, final String email, final boolean expectedResult) {
    // GIVEN existsByEmail_data_provider

    // WHEN
    final boolean result = userRepository.existsByEmail(email);

    // THEN
    assertThat(result).isEqualTo(expectedResult);
  }

  static Stream<Arguments> existsByUsername_data_provider() {
    return Stream.of(
        Arguments.of("Existing username", "TEST", true),
        Arguments.of("Non-existing user", "FAKE", false));
  }

  @DatabaseSetup(DBUNIT_XML)
  @ParameterizedTest
  @MethodSource("existsByUsername_data_provider")
  void existsByUsername_should_return_expected_results(
      final String testCase, final String email, final boolean expectedResult) {
    // GIVEN existsByUsername_data_provider

    // WHEN
    final boolean result = userRepository.existsByUsername(email);

    // THEN
    assertThat(result).isEqualTo(expectedResult);
  }
}
