package mx.iwa.demostripe.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@EqualsAndHashCode(of = {"id"})
@Getter
public class PermissionEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Setter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Integer id;

  @Column(name = "keycode", nullable = false, length = 25, unique = true)
  private String keycode;

  @Column(name = "name", nullable = false, length = 100)
  private String name;
}
