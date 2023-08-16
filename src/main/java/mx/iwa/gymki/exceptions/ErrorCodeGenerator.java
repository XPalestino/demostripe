package mx.iwa.gymki.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodeGenerator {

  public static String generate() {
    return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
