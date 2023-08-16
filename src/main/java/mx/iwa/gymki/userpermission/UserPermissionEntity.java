package mx.iwa.gymki.userpermission;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_permissions")
@EqualsAndHashCode(of = {"id"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPermissionEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId UserPermissionEntityId id;

  public UserPermissionEntity(final UserPermissionEntityId id) {
    this.id = id;
  }
}
