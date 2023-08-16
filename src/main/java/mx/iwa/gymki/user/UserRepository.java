package mx.iwa.gymki.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
@Validated
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  boolean existsByUsernameAndIdNot(@NotBlank String username, @Min(1) Integer excludeUnitId);

  boolean existsByEmailAndIdNot(@NotBlank String email, @Min(1) Integer excludeUnitId);

  boolean existsByUsername(@NotBlank String username);

  boolean existsByEmail(@NotBlank String email);
}
