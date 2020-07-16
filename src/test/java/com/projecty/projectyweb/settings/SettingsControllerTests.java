package com.projecty.projectyweb.settings;

import com.projecty.projectyweb.ProjectyWebApplication;
import com.projecty.projectyweb.user.User;
import com.projecty.projectyweb.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectyWebApplication.class)
@AutoConfigureMockMvc
public class SettingsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void init() {
        User user = User.builder()
                .id(1L)
                .username("user")
                .settings(new Settings())
                .build();
        Mockito.when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(java.util.Optional.of(user));
    }


    @Test
    @WithMockUser()
    public void givenRequestOnGetSettings_shouldReturnSettings() throws Exception {
        mockMvc.perform(get("/settings"))
                .andExpect(jsonPath("$.isProjectEmailNotificationsEnabled").isBoolean())
                .andExpect(status().isOk());
    }
}
