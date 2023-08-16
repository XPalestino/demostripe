package mx.iwa.gymki.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class UpdateUserRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @Size(max = 150)
  private final String fullName;

  @NotBlank
  @Email
  @Size(max = 100)
  private final String email;

  private final boolean active;
}
