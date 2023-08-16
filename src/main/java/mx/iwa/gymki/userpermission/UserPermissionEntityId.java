package mx.iwa.gymki.userpermission;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPermissionEntityId implements Serializable {

  @Column(name = "users_id")
  private int userId;

  @Column(name = "permissions_id")
  private int permissionId;
}
