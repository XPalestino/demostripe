package mx.iwa.demostripe.permission;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PermissionResource {
  private static final long serialVersionUID = 1L;

  private Integer id;

  private String keycode;

  private String name;

  public static PermissionResource build(final PermissionEntity permission) {

    return PermissionResource.builder()
        .id(permission.getId())
        .name(permission.getName())
        .keycode(permission.getKeycode())
        .build();
  }
}
