package mx.iwa.gymki.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessages {
  // GENERAL
  INTERNAL_SERVER_ERROR("Internal server error"),
  INTEGRITY_VIOLATION("Data integrity violation"),
  VALIDATION("Validation errors"),

  // USER
  USER_000("User not found"),
  USER_001("User email already registered"),
  USER_002("User username already registered");

  private final String description;
}
