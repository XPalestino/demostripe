package mx.iwa.gymki.permission;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionService permissionService;

  @GetMapping
  public ResponseEntity<List<PermissionResource>> findAllPermissions() {
    return ResponseEntity.ok(permissionService.findAllPermissions());
  }
}
