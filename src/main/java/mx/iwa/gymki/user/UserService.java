package mx.iwa.gymki.user;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.iwa.gymki.enums.ErrorMessages;
import mx.iwa.gymki.exceptions.ConflictException;
import mx.iwa.gymki.exceptions.NotFoundException;
import mx.iwa.gymki.service.ReadOnlyTransaction;
import mx.iwa.gymki.service.ReadWriteTransaction;
import mx.iwa.gymki.utils.ValidateHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ReadWriteTransaction
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @ReadOnlyTransaction
  public List<UserResource> getAll(final Pageable pageable) {

    final Page<UserEntity> users = userRepository.findAll(pageable);

    return users.stream().map(UserResource::build).toList();
  }

  @ReadOnlyTransaction
  public long countAll() {
    return userRepository.count();
  }

  @ReadOnlyTransaction
  public void validateIfUserExists(final int userId) {
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException("User does not exist: " + userId, ErrorMessages.USER_000);
    }
  }

  @ReadOnlyTransaction
  public UserResource getUserDtoById(final int userId) {
    final UserEntity userEntity = getUserById(userId);
    return UserResource.build(userEntity);
  }

  public UserResource createUser(final NewUserRequest request) {
    validateIfUserNameDataIsAlreadyRegistered(request.getUsername());
    final String encodedPassword = encoder.encode(request.getPassword());
    final UserEntity userEntity = new UserEntity(request, encodedPassword);
    userRepository.save(userEntity);
    log.info("User has been created successfully: {}", userEntity.getUsername());

    return UserResource.build(userEntity);
  }

  public void deleteUser(final int userId) {
    final UserEntity userEntity = getUserById(userId);

    userRepository.delete(userEntity);
    log.info("User has been deleted");
  }

  private UserEntity getUserById(final int userId) {
    final Optional<UserEntity> user = userRepository.findById(userId);
    ValidateHelper.checkNull(user, UserEntity.class, userId, ErrorMessages.USER_000);
    return user.get();
  }

  public UserResource updateUser(final int userId, final UpdateUserRequest request) {
    final UserEntity userEntity = getUserById(userId);
    validateIfUserDataIsAlreadyRegistered(userEntity.getUsername(), request.getEmail(), userId);
    userEntity.update(request);
    userRepository.save(userEntity);
    log.info("User has been updated: {}", userEntity.getUsername());

    return UserResource.build(userEntity);
  }

  private void validateIfUserDataIsAlreadyRegistered(
      final String username, final String email, final Integer userId) {
    if (userRepository.existsByUsernameAndIdNot(username, userId)) {
      throw new ConflictException(
          "User already registered with username: " + username, ErrorMessages.USER_002);
    }

    if (userRepository.existsByEmailAndIdNot(email, userId)) {
      throw new ConflictException(
          "There is a user already registered with email: " + email, ErrorMessages.USER_001);
    }
  }

  private void validateIfUserNameDataIsAlreadyRegistered(final String username) {
    if (userRepository.existsByUsername(username)) {
      throw new ConflictException(
          "There is  a user already registered with username: " + username, ErrorMessages.USER_002);
    }
  }
}
