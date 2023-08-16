package mx.iwa.demostripe.permission;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mx.iwa.demostripe.service.ReadOnlyTransaction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
  private final PermissionRepository permissionRepository;

  @ReadOnlyTransaction
  public List<PermissionResource> findAllPermissions() {
    final List<PermissionEntity> permissions = permissionRepository.findAll();

    return permissions.stream().map(PermissionResource::build).toList();
  }
}
