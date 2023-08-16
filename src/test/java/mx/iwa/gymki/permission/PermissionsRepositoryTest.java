package mx.iwa.gymki.permission;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import mx.iwa.gymki.BaseRepositoryTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

public class PermissionsRepositoryTest extends BaseRepositoryTest {
  private static final String DBUNIT_XML = "classpath:dbunit/repository/permissions.xml";

  @Autowired private PermissionRepository permissionRepository;

  static Stream<Arguments> findAllByIdIsIn_data_provider() {
    return Stream.of(
        Arguments.of("Existing permissions Ids", List.of(1, 2), List.of(1, 2)),
        Arguments.of("Non-existing permissions Ids", List.of(99), Collections.emptyList()));
  }

  @DatabaseSetup(DBUNIT_XML)
  @ParameterizedTest
  @MethodSource("findAllByIdIsIn_data_provider")
  void findAllByIdIsIn_should_return_expected_results(
      final String testCase,
      final Collection<Integer> permissionIds,
      final List<Integer> expectedResults) {
    // GIVEN findAllByIdIsIn_should_return_expected

    // WHEN
    final List<PermissionEntity> permissions = permissionRepository.findAllByIdIsIn(permissionIds);

    // THEN
    assertThat(permissions)
        .extracting(PermissionEntity::getId)
        .containsExactlyElementsOf(expectedResults);
  }
}
