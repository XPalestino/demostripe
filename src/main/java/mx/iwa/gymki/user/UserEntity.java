package mx.iwa.gymki.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "users")
@Getter
@ToString(exclude = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Setter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @Column(name = "username", unique = true, nullable = false, length = 100)
  private String username;

  @Column(name = "password", nullable = false, length = 150)
  private String password;

  @Column(name = "full_name", nullable = false, length = 150)
  private String fullName;

  @Column(name = "email", unique = true, nullable = false, length = 100)
  private String email;

  @Column(name = "is_active", nullable = false)
  private boolean active;

  public UserEntity(final NewUserRequest request, final String password) {
    this.fullName = request.getFullName();
    this.username = request.getUsername();
    this.email = request.getEmail();
    this.active = request.isActive();
    this.password = password;
  }

  public void update(final UpdateUserRequest request) {
    this.fullName = request.getFullName();
    this.email = request.getEmail();
    this.active = request.isActive();
  }
}
