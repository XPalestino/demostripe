package mx.iwa.demostripe.permission;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PermissionController.class)
@AutoConfigureMockMvc
public class PermissionControllerMockMvcTest {
  private static final String PERMISSIONS_URL = "/permissions";

  @MockBean private PermissionService permissionService;

  @Autowired private MockMvc mockMvc;

  @Test
  void findAllPermissions_should_return_expected_results() throws Exception {
    final List<PermissionResource> userPermissions =
        List.of(
            PermissionResource.build(mock(PermissionEntity.class)),
            PermissionResource.build(mock(PermissionEntity.class)));

    when(permissionService.findAllPermissions()).thenReturn(userPermissions);

    mockMvc
        .perform(get(PERMISSIONS_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(userPermissions.size()));
  }
}
