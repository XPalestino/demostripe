package mx.iwa.demostripe.userpermission;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import mx.iwa.demostripe.permission.PermissionEntity;
import mx.iwa.demostripe.permission.PermissionResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserPermissionController.class)
@AutoConfigureMockMvc
public class UserPermissionControllerMockMvcTest {
  private static final String USERS_PERMISSIONS_URL = "/users/{userId}/permissions";
  private static final int userId = 1;

  @MockBean private UserPermissionService userPermissionService;
  @Autowired private ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @Test
  void getAllPermissionsByUserId_should_return_expected_results() throws Exception {
    final List<PermissionResource> userPermissions =
        List.of(
            PermissionResource.build(mock(PermissionEntity.class)),
            PermissionResource.build(mock(PermissionEntity.class)));

    when(userPermissionService.getAllPermissionsByUserId(userId)).thenReturn(userPermissions);

    mockMvc
        .perform(get(USERS_PERMISSIONS_URL, userId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(userPermissions.size()));
  }

  @Test
  void addPermissions_should_return_expected_results() throws Exception {
    final UserPermissionRequest userPermissions = mock(UserPermissionRequest.class);
    when(userPermissions.getPermissionIds()).thenReturn(Set.of(1, 2, 3));

    when(userPermissionService.addPermissions(userId, userPermissions)).thenReturn(1);

    mockMvc
        .perform(
            post(USERS_PERMISSIONS_URL, userId)
                .content(objectMapper.writeValueAsString(userPermissions))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
