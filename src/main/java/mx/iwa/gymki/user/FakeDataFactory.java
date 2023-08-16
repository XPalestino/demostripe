package mx.iwa.gymki.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FakeDataFactory {
  private static final Faker faker = new Faker();

  public static UserEntity anyUserEntityWithId(final int id) {

    final String password = faker.internet().password(8, 25, true, true, false);

    final UserEntity user = new UserEntity(anyNewUserRequest(password), password);
    user.setId(id);
    return user;
  }

  public static NewUserRequest anyNewUserRequest(final String password) {
    final String username = faker.name().firstName();
    final String fullName = faker.name().fullName();
    final String email = faker.internet().emailAddress();
    final boolean active = faker.bool().bool();
    return new NewUserRequest(username, fullName, password, email, active);
  }
}
