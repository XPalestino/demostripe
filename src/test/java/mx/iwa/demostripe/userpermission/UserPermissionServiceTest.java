package mx.iwa.demostripe.userpermission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import mx.iwa.demostripe.permission.PermissionEntity;
import mx.iwa.demostripe.permission.PermissionRepository;
import mx.iwa.demostripe.permission.PermissionResource;
import mx.iwa.demostripe.user.UserService;
import mx.iwa.demostripe.utils.CollectionBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UserPermissionServiceTest {
  private static final int TEST_USER_ID = 1;
  private UserPermissionService userPermissionService;

  private UserPermissionRepository userPermissionRepository;
  private PermissionRepository permissionRepository;

  @BeforeEach
  void setup() {
    userPermissionRepository = mock();
    permissionRepository = mock();
    final UserService userService = mock();
    userPermissionService =
        new UserPermissionService(permissionRepository, userPermissionRepository, userService);
  }

  @Test()
  void addPermissions_should_assign_permissions_to_user() {
    final UserPermissionRequest user = new UserPermissionRequest();
    user.setPermissionIds(Set.of(1, 2));

    when(userPermissionRepository.deleteAllByIdUserId(TEST_USER_ID)).thenReturn(1);

    userPermissionService.addPermissions(TEST_USER_ID, user);

    verify(userPermissionRepository).deleteAllByIdUserId(TEST_USER_ID);
  }

  static Stream<Arguments> getAllPermissionsByUserId_data_provider() {
    return Stream.of(
        Arguments.of(
            "Existing user permissions",
            List.of(new UserPermissionEntity(new UserPermissionEntityId(1, 1))),
            List.of(mock(PermissionResource.class))),
        Arguments.of("Empty user permissions", Collections.emptyList(), Collections.emptyList()));
  }

  @ParameterizedTest
  @MethodSource("getAllPermissionsByUserId_data_provider")
  void getAllPermissionsByUserId_should_return_expected(
      final String testCase,
      final List<UserPermissionEntity> userPermissionEntities,
      final List<PermissionResource> resources) {
    final PermissionEntity permissionEntity = new PermissionEntity();
    permissionEntity.setId(1);

    final List<PermissionEntity> permissionEntities = List.of(permissionEntity);

    final Set<Integer> permissionIds =
        CollectionBuilderUtil.getValues(
            userPermissionEntities, userPermission -> userPermission.getId().getPermissionId());

    when(userPermissionRepository.findAllByIdUserId(TEST_USER_ID))
        .thenReturn(userPermissionEntities);

    when(permissionRepository.findAllByIdIsIn(permissionIds)).thenReturn(permissionEntities);

    final List<PermissionResource> permissionResources =
        userPermissionService.getAllPermissionsByUserId(TEST_USER_ID);

    assertThat(permissionResources).hasSameSizeAs(resources);
    verify(userPermissionRepository).findAllByIdUserId(TEST_USER_ID);
  }
}
