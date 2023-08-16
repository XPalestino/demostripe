package mx.iwa.demostripe.permission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PermissionServiceTest {
  private PermissionService permissionService;

  private PermissionRepository permissionRepository;

  @BeforeEach
  void setup() {
    permissionRepository = mock();
    permissionService = new PermissionService(permissionRepository);
  }

  @Test()
  void findAllPermissions_should_return_values() {
    final List<PermissionEntity> permissionEntities =
        List.of(mock(PermissionEntity.class), mock(PermissionEntity.class));

    when(permissionRepository.findAll()).thenReturn(permissionEntities);

    final List<PermissionResource> users = permissionService.findAllPermissions();

    assertThat(users).hasSameSizeAs(permissionEntities);
  }
}
