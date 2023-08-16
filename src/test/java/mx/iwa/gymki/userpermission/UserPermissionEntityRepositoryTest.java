package mx.iwa.gymki.userpermission;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import mx.iwa.gymki.BaseRepositoryTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPermissionEntityRepositoryTest extends BaseRepositoryTest {
  private static final String DBUNIT_XML = "classpath:dbunit/repository/users_permissions.xml";

  @Autowired private UserPermissionRepository userPermissionRepository;

  static Stream<Arguments> findAllByIdUserId_data_provider() {
    return Stream.of(
        Arguments.of("Existing user ID", 1, List.of(1, 2)),
        Arguments.of("Non-existing user ID", 99, Collections.emptyList()));
  }

  @ParameterizedTest
  @MethodSource("findAllByIdUserId_data_provider")
  @DatabaseSetup(DBUNIT_XML)
  void findAllByIdUserId_should_return_expected(
      final String testCase, final Integer userId, final List<Integer> expectedRoles) {
    // GIVEN findAllByIdUserId_data_provider

    // WHEN
    final List<UserPermissionEntity> userPermissions =
        userPermissionRepository.findAllByIdUserId(userId);

    // THEN
    assertThat(userPermissions)
        .extracting(UserPermissionEntity::getId)
        .extracting(UserPermissionEntityId::getPermissionId)
        .containsExactlyElementsOf(expectedRoles);
  }

  static Stream<Arguments> deleteAllByIdUserId_data_provider() {
    return Stream.of(
        Arguments.of("Existing user ID", 1, 2), Arguments.of("Non-existing user ID", 99, 0));
  }

  @ParameterizedTest
  @MethodSource("deleteAllByIdUserId_data_provider")
  @DatabaseSetup(DBUNIT_XML)
  void deleteAllByIdUserId_should_delete_expected_user(
      final String testCase, final Integer userId, final int expectedResult) {
    // GIVEN deleteAllByIdUserId_data_provider

    // WHEN
    final int result = userPermissionRepository.deleteAllByIdUserId(userId);

    // THEN
    assertThat(result).isEqualTo(expectedResult);
  }
}
