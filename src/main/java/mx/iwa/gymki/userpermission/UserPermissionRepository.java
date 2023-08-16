package mx.iwa.gymki.userpermission;

import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository
    extends CrudRepository<UserPermissionEntity, UserPermissionEntityId> {
  int deleteAllByIdUserId(@Min(1) int userId);

  List<UserPermissionEntity> findAllByIdUserId(@Min(1) Integer userId);
}
