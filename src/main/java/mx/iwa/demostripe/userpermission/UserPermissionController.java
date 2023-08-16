package mx.iwa.demostripe.userpermission;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mx.iwa.demostripe.permission.PermissionResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/permissions")
@RequiredArgsConstructor
public class UserPermissionController {
  private final UserPermissionService userPermissionService;

  @PostMapping
  public ResponseEntity<Integer> addPermissions(
      @PathVariable("userId") final int userId,
      @RequestBody @Valid final UserPermissionRequest request) {

    return ResponseEntity.ok(userPermissionService.addPermissions(userId, request));
  }

  @GetMapping
  public ResponseEntity<List<PermissionResource>> getAllPermissionsByUserId(
      @PathVariable("userId") final int userId) {
    return ResponseEntity.ok(userPermissionService.getAllPermissionsByUserId(userId));
  }
}
