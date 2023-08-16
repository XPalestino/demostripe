package mx.iwa.gymki.test.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.iwa.gymki.enums.ErrorMessages;
import mx.iwa.gymki.exceptions.ConflictException;
import mx.iwa.gymki.exceptions.NotFoundException;
import mx.iwa.gymki.user.UpdateUserRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
@RequiredArgsConstructor
public class FakeController {
  @GetMapping("/internal-server-error")
  public void handleInternalServerError() {
    throw new RuntimeException("Test exception");
  }

  @GetMapping("/conflict")
  public void handleConflictException() {
    throw new ConflictException("There is  a user already registered", ErrorMessages.USER_002);
  }

  @GetMapping("/not-found")
  public void handleNotFoundException() {
    throw new NotFoundException("User does not exist", ErrorMessages.USER_000);
  }

  @GetMapping("/data-integrity-violation")
  public void handleDataIntegrityViolationException() {
    throw new DataIntegrityViolationException("Data integrity violation exception");
  }

  @PostMapping("/validation")
  public void handleMethodArgumentNotValidException(
      @RequestBody @Valid final UpdateUserRequest request) {}
}
