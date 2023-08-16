package mx.iwa.demostripe.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CollectionBuilderUtilTest {

  static Stream<Arguments> getValues_data_provider() {
    return Stream.of(
        Arguments.of(
            "Should remove duplicated values",
            List.of(
                new MockPerson(1, "JOSE"), new MockPerson(2, "PEDRO"), new MockPerson(3, "PEDRO")),
            List.of("JOSE", "PEDRO")),
        Arguments.of(
            "Should remove null values",
            List.of(new MockPerson(1, "JOSE"), new MockPerson(2, null), new MockPerson(3, null)),
            List.of("JOSE")),
        Arguments.of("Collections empty list", Collections.emptyList(), Collections.emptyList()));
  }

  @ParameterizedTest
  @MethodSource("getValues_data_provider")
  void getValues_should_return_expected_results(
      final String testCase, final List<MockPerson> items, final List<String> expectedNames) {

    final Set<String> names = CollectionBuilderUtil.getValues(items, MockPerson::name);

    assertThat(names).containsExactlyElementsOf(expectedNames);
  }

  record MockPerson(int id, String name) {}
}
