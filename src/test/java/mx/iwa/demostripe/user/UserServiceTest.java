package mx.iwa.demostripe.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mx.iwa.demostripe.exceptions.ConflictException;
import mx.iwa.demostripe.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

  private UserService userService;
  private UserRepository userRepository;
  private UserEntity userEntity;

  @BeforeEach
  void setup() {
    final PasswordEncoder encoder = mock(PasswordEncoder.class);
    userRepository = mock();
    userService = new UserService(userRepository, encoder);
    userEntity = mock();
    when(userEntity.getId()).thenReturn(1);
  }

  @Test
  void createUser_should_return_expected() {

    // GIVEN
    final NewUserRequest newUserRequest =
        new NewUserRequest("GARELLANO", "IWA", "qwertyuio", "garellano@iwa.com.mx", false);

    when(userEntity.getUsername()).thenReturn("GARELLANO");

    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

    // WHEN
    final UserResource created = userService.createUser(newUserRequest);

    // THEN
    assertThat(created.getUsername()).isSameAs(userEntity.getUsername());
  }

  @Test
  void deleteUser_should_delete_expected_user() {
    // GIVEN
    // WHEN
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));

    userService.deleteUser(anyInt());

    // THEN
    verify(userRepository).delete(userEntity);
  }

  @Test()
  void getAll_should_return_expected() {
    // GIVEN
    // WHEN
    final Page<UserEntity> userPage =
        new PageImpl<>(List.of(mock(UserEntity.class), mock(UserEntity.class)));
    when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

    // WHEN
    final List<UserResource> users = userService.getAll(PageRequest.of(0, 10));

    // THEN
    assertThat(users).hasSameSizeAs(userPage.getContent());
  }

  @Test
  void getUserDtoById_should_return_expected_resource() {
    when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
    final UserResource userResource = userService.getUserDtoById(userEntity.getId());
    assertThat(userResource.getId()).isEqualTo(userEntity.getId());
  }

  @Test
  void updateUser_should_return_expected() {

    final UpdateUserRequest updateUserRequest =
        new UpdateUserRequest("IWA TEST", "update@iwa.com.mx", true);

    final UserEntity userEntity = new UserEntity();
    userEntity.setId(1);

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));

    userRepository.save(userEntity);
    final UserResource update = userService.updateUser(userEntity.getId(), updateUserRequest);

    assertThat(update.getUsername()).isSameAs(userEntity.getUsername());
    assertThat(update.getFullName()).isSameAs(userEntity.getFullName());
    assertThat(update.getEmail()).isSameAs(userEntity.getEmail());
    assertThat(update.isActive()).isSameAs(userEntity.isActive());
  }

  @Test
  void validateIfUserExists_should_throw_not_found_exception() {
    when(userRepository.existsById(anyInt())).thenReturn(false);

    assertThatThrownBy(() -> userService.validateIfUserExists(userEntity.getId()))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("User does not exist: " + userEntity.getId());
  }

  @Test
  void validateIfUserExists_should_return_expected() {
    when(userRepository.existsById(anyInt())).thenReturn(true);

    userService.validateIfUserExists(userEntity.getId());

    verify(userRepository).existsById(anyInt());
  }

  @Test
  void updateUser_should_throw_conflict_exception_when_user_exist() {

    final UpdateUserRequest updateUserRequest = getUpdateUserRequest();

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));
    when(userEntity.getUsername()).thenReturn("USER UPDATE");
    when(userRepository.existsByUsernameAndIdNot(anyString(), anyInt())).thenReturn(true);

    assertThatThrownBy(() -> userService.updateUser(anyInt(), updateUserRequest))
        .isInstanceOf(ConflictException.class)
        .hasMessage("User already registered with username: USER UPDATE");
  }

  @Test
  void updateUser_should_throw_conflict_exception_when_email_exist() {

    final UpdateUserRequest updateUserRequest = getUpdateUserRequest();

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));
    when(userRepository.existsByEmailAndIdNot(anyString(), anyInt())).thenReturn(true);

    assertThatThrownBy(() -> userService.updateUser(anyInt(), updateUserRequest))
        .isInstanceOf(ConflictException.class)
        .hasMessage("There is a user already registered with email: update@iwa.com.mx");
  }

  @Test
  void createUser_should_throw_conflict_exception_when_user_exist() {

    final NewUserRequest newUserRequest =
        new NewUserRequest("USER UPDATE", "IWA", "QWERTY", "garellano@iwa.com.mx", false);

    when(userRepository.existsByUsername(anyString())).thenReturn(true);

    assertThatThrownBy(() -> userService.createUser(newUserRequest))
        .isInstanceOf(ConflictException.class)
        .hasMessage("There is  a user already registered with username: USER UPDATE");
  }

  @Test()
  void countAll_should_return_expected() {

    final Page<UserEntity> userPage =
        new PageImpl<>(List.of(mock(UserEntity.class), mock(UserEntity.class)));

    when(userRepository.count()).thenReturn(userPage.stream().count());

    final long countAll = userService.countAll();

    assertThat(countAll).isEqualTo(userPage.stream().count());
  }

  private UpdateUserRequest getUpdateUserRequest() {
    return new UpdateUserRequest("IWA TEST", "update@iwa.com.mx", true);
  }
}
