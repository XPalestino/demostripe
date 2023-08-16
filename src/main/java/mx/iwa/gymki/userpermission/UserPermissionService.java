package mx.iwa.gymki.userpermission;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.iwa.gymki.permission.PermissionEntity;
import mx.iwa.gymki.permission.PermissionRepository;
import mx.iwa.gymki.permission.PermissionResource;
import mx.iwa.gymki.service.ReadOnlyTransaction;
import mx.iwa.gymki.service.ReadWriteTransaction;
import mx.iwa.gymki.user.UserService;
import mx.iwa.gymki.utils.CollectionBuilderUtil;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ReadWriteTransaction
public class UserPermissionService {

  private final PermissionRepository permissionRepository;
  private final UserPermissionRepository userPermissionRepository;
  private final UserService userService;

  public int addPermissions(final int userId, final UserPermissionRequest request) {
    userService.validateIfUserExists(userId);

    userPermissionRepository.deleteAllByIdUserId(userId);
    assignPermissionsToUser(userId, request.getPermissionIds());
    log.info("User ID {} permissions added successfully", userId);

    return userId;
  }

  @ReadOnlyTransaction
  public List<PermissionResource> getAllPermissionsByUserId(final int userId) {
    final List<UserPermissionEntity> userPermissions =
        userPermissionRepository.findAllByIdUserId(userId);

    final Set<Integer> permissionIds =
        CollectionBuilderUtil.getValues(
            userPermissions, userPermission -> userPermission.getId().getPermissionId());
    if (permissionIds.isEmpty()) {
      return Collections.emptyList();
    }
    final List<PermissionEntity> permissions = permissionRepository.findAllByIdIsIn(permissionIds);
    return permissions.stream().map(PermissionResource::build).toList();
  }

  private void assignPermissionToUser(final UserPermissionEntityId userPermissionEntityId) {
    final UserPermissionEntity userPermission = new UserPermissionEntity(userPermissionEntityId);
    userPermissionRepository.save(userPermission);
    log.info(
        "Permission ID {} assigned to user ID {}",
        userPermissionEntityId.getPermissionId(),
        userPermissionEntityId.getUserId());
  }

  public void assignPermissionsToUser(final Integer userId, final Set<Integer> permissionsIds) {
    permissionsIds.stream()
        .map(permissionId -> new UserPermissionEntityId(userId, permissionId))
        .forEach(this::assignPermissionToUser);
  }
}
