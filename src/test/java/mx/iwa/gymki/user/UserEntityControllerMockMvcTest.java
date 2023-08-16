package mx.iwa.gymki.user;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserEntityControllerMockMvcTest {
  private static final String USERS_URL = "/users";
  private static final String USER_URL = USERS_URL + "/{userID}";

  private static final int userId = 1;

  private static final UserEntity user = mock(UserEntity.class);
  private static final UserResource USER_RESOURCE = UserResource.build(user);
  @MockBean private UserService userService;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @Test
  void delete_should_return_expected_results() throws Exception {
    mockMvc.perform(delete(USER_URL, userId)).andExpect(status().isNoContent());
  }

  @Test
  void createUser_should_return_expected_results() throws Exception {
    final NewUserRequest userRequest = buildCreateUserRequest();

    when(userService.createUser(userRequest)).thenReturn(USER_RESOURCE);

    mockMvc
        .perform(
            post(USERS_URL)
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").exists());
  }

  @Test
  void create_should_not_create_user_with_invalid_request() throws Exception {

    final NewUserRequest newUserRequest = buildInvalidCreateUserRequest();

    when(userService.createUser(newUserRequest)).thenReturn(USER_RESOURCE);

    mockMvc
        .perform(
            post(USERS_URL)
                .content(objectMapper.writeValueAsString(newUserRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.details", hasSize(2)),
            jsonPath(
                "$.details",
                containsInAnyOrder(
                    Map.of("message", "must not be blank", "target", "username"),
                    Map.of("message", "size must be between 8 and 25", "target", "password"))),
            jsonPath("$.message").value("Validation errors"));
  }

  @Test
  void updateUser_should_return_expected_results() throws Exception {

    final UpdateUserRequest updateUserRequest =
        new UpdateUserRequest("IWA", "garellano@iwa.com.mx", false);

    when(userService.updateUser(userId, updateUserRequest)).thenReturn(USER_RESOURCE);

    mockMvc
        .perform(
            patch(USER_URL, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());
  }

  @Test
  void update_should_not_update_user_with_invalid_form() throws Exception {

    final UpdateUserRequest updateUserRequest = buildInvalidUpdateUserRequest();

    when(userService.updateUser(userId, updateUserRequest)).thenReturn(USER_RESOURCE);

    mockMvc
        .perform(
            patch(USER_URL, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserRequest)))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.details", hasSize(2)),
            jsonPath(
                "$.details",
                containsInAnyOrder(
                    Map.of("message", "must not be blank", "target", "fullName"),
                    Map.of("message", "must not be blank", "target", "email"))),
            jsonPath("$.message").value("Validation errors"));
  }

  @Test
  void getUserById_should_return_expected_results() throws Exception {

    when(userService.getUserDtoById(userId)).thenReturn(USER_RESOURCE);

    mockMvc
        .perform(get(USER_URL, userId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());
  }

  @Test
  void getAllUsers_should_return_expected_results() throws Exception {
    final List<UserResource> users =
        List.of(
            UserResource.build(FakeDataFactory.anyUserEntityWithId(1)),
            UserResource.build(FakeDataFactory.anyUserEntityWithId(2)));

    when(userService.getAll(any(Pageable.class))).thenReturn(users);

    when(userService.countAll()).thenReturn((long) users.size());

    mockMvc
        .perform(
            get(USERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10"))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.totalElements").value(users.size()),
            jsonPath("$.data.content[0].id").value(users.get(0).getId()),
            jsonPath("$.data.content[1].id").value(users.get(1).getId()),
            jsonPath("$.page.number").value(1),
            jsonPath("$.page.size").value(10));
  }

  private NewUserRequest buildCreateUserRequest() {
    return new NewUserRequest("GARELLANO", "IWA", "qwertyuio", "garellano@iwa.com.mx", false);
  }

  private NewUserRequest buildInvalidCreateUserRequest() {
    return new NewUserRequest(
        null, "IWA", "09876543210987654321098765", "garellano@iwa.com.mx", false);
  }

  private UpdateUserRequest buildInvalidUpdateUserRequest() {
    return new UpdateUserRequest(null, null, false);
  }
}
