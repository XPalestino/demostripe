package mx.iwa.gymki.permission;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mx.iwa.gymki.service.ReadOnlyTransaction;
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
