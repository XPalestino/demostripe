package mx.iwa.demostripe.permission;

import jakarta.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
@Validated
public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {
  List<PermissionEntity> findAllByIdIsIn(@NotEmpty Collection<Integer> permissionIds);
}
