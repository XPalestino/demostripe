package mx.iwa.gymki.userpermission;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data
public class UserPermissionRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotEmpty private Set<Integer> permissionIds;
}
