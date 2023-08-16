package mx.iwa.demostripe.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class NewUserRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @Size(max = 100)
  private final String username;

  @NotBlank
  @Size(max = 150)
  private final String fullName;

  @NotBlank
  @Size(min = 8, max = 25)
  private final String password;

  @NotBlank
  @Email
  @Size(max = 100)
  private final String email;

  private final boolean active;
}
