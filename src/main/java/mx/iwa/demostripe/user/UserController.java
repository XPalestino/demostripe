package mx.iwa.demostripe.user;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mx.iwa.demostripe.resource.DataResource;
import mx.iwa.demostripe.resource.PageResource;
import mx.iwa.demostripe.resource.PagedResources;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResource> create(@RequestBody @Valid final NewUserRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body((userService.createUser(request)));
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<UserResource> update(
      @PathVariable("userId") final int userId,
      @RequestBody @Valid final UpdateUserRequest request) {

    return ResponseEntity.ok(userService.updateUser(userId, request));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> delete(@PathVariable("userId") final int userId) {
    userService.deleteUser(userId);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResource> getUserById(@PathVariable("userId") final int userId) {
    return ResponseEntity.ok(userService.getUserDtoById(userId));
  }

  @GetMapping
  public ResponseEntity<PagedResources<UserResource>> getAllUsers(
      @SortDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {

    final List<UserResource> userResources = userService.getAll(pageable);

    final DataResource<UserResource> users = DataResource.<UserResource>build(userResources);

    return ResponseEntity.ok(
        PagedResources.build(
            users,
            userService.countAll(),
            PageResource.build(pageable.getPageNumber(), pageable.getPageSize())));
  }
}
