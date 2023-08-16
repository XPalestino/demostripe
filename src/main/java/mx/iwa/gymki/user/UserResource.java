package mx.iwa.gymki.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserResource {
  private final Integer id;

  private final String username;

  private final String fullName;

  private final String email;

  private final boolean active;

  public static UserResource build(final UserEntity userEntity) {
    return UserResource.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .email(userEntity.getEmail())
        .fullName(userEntity.getFullName())
        .active(userEntity.isActive())
        .build();
  }
}
