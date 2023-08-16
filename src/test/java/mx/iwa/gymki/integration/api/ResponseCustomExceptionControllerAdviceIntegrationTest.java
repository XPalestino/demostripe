package mx.iwa.gymki.integration.api;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import mx.iwa.gymki.test.api.FakeController;
import mx.iwa.gymki.user.UpdateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FakeController.class)
@AutoConfigureMockMvc
public class ResponseCustomExceptionControllerAdviceIntegrationTest {
  private static final String EXCEPTIONS_URL = "/exceptions";
  private static final String INTERNAL_SERVER_ERROR_URL = EXCEPTIONS_URL + "/internal-server-error";
  private static final String CONFLICT_EXCEPTION_URL = EXCEPTIONS_URL + "/conflict";
  private static final String NOT_FOUND_EXCEPTION_URL = EXCEPTIONS_URL + "/not-found";

  private static final String DATA_INTEGRITY_VIOLATION_EXCEPTION_URL =
      EXCEPTIONS_URL + "/data-integrity-violation";
  private static final String VALIDATION_URL = EXCEPTIONS_URL + "/validation";

  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;

  @Test
  void handleException_should_throw_internal_server_error() throws Exception {
    mockMvc
        .perform(get(INTERNAL_SERVER_ERROR_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isInternalServerError(),
            jsonPath("$.message").value("Internal server error"),
            jsonPath("$.code").value("INTERNAL_SERVER_ERROR"),
            jsonPath("$.timestamp").exists());
  }

  @Test
  void handleConflictException_should_throw_conflict_exception() throws Exception {
    mockMvc
        .perform(get(CONFLICT_EXCEPTION_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isConflict(),
            jsonPath("$.message").value("User username already registered"),
            jsonPath("$.code").value("USER_002"),
            jsonPath("$.timestamp").exists());
  }

  @Test
  void handleNotFoundException_should_throw_not_found_exception() throws Exception {
    mockMvc
        .perform(get(NOT_FOUND_EXCEPTION_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.message").value("User not found"),
            jsonPath("$.code").value("USER_000"),
            jsonPath("$.timestamp").exists());
  }

  @Test
  void handleDataIntegrityViolationException_should_throw_data_integrity_violation_exception()
      throws Exception {
    mockMvc
        .perform(
            get(DATA_INTEGRITY_VIOLATION_EXCEPTION_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isConflict(),
            jsonPath("$.message").value("Data integrity violation"),
            jsonPath("$.code").value("INTEGRITY_VIOLATION"),
            jsonPath("$.timestamp").exists());
  }

  @Test
  void handleMethodArgumentNotValidException_should_throw_method_argument_not_validation_exception()
      throws Exception {

    final UpdateUserRequest fakeRequest = new UpdateUserRequest("", "email", false);

    mockMvc
        .perform(
            post(VALIDATION_URL)
                .content(objectMapper.writeValueAsString(fakeRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.details", hasSize(2)),
            jsonPath(
                "$.details",
                containsInAnyOrder(
                    Map.of("message", "must not be blank", "target", "fullName"),
                    Map.of("message", "must be a well-formed email address", "target", "email"))),
            jsonPath("$.message").value("Validation errors"));
  }
}
