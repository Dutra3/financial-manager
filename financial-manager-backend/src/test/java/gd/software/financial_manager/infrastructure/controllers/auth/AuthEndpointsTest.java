package gd.software.financial_manager.infrastructure.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.infrastructure.dtos.GoogleLoginRequest;
import gd.software.financial_manager.infrastructure.dtos.LoginRequest;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.UserRepository;
import gd.software.financial_manager.infrastructure.utils.GoogleTokenValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private GoogleTokenValidator googleTokenValidator;

    @Test
    void should_login_and_return_token() throws Exception {
        userRepository.save(UserRow.builder()
                .email("login@test.com")
                .password("secret123")
                .build());

        LoginRequest request = new LoginRequest("login@test.com", "secret123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("login@test.com"));
    }

    @Test
    void should_return_401_when_credentials_invalid() throws Exception {
        LoginRequest request = new LoginRequest("wrong@test.com", "wrongpass");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_google_login_create_new_user_and_return_token() throws Exception {
        when(googleTokenValidator.validate(anyString()))
                .thenReturn(new GoogleTokenValidator.GoogleUserInfo("google-sub-123", "googleuser@test.com"));

        GoogleLoginRequest request = new GoogleLoginRequest("valid-google-token");

        mockMvc.perform(post("/auth/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("googleuser@test.com"));
    }

    @Test
    void should_google_login_return_existing_user_token() throws Exception {
        userRepository.save(UserRow.builder()
                .email("existinggoogle@test.com")
                .provider("google")
                .providerId("google-sub-456")
                .build());

        when(googleTokenValidator.validate(anyString()))
                .thenReturn(new GoogleTokenValidator.GoogleUserInfo("google-sub-456", "existinggoogle@test.com"));

        GoogleLoginRequest request = new GoogleLoginRequest("valid-google-token");

        mockMvc.perform(post("/auth/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("existinggoogle@test.com"));
    }

    @Test
    void should_return_401_when_google_token_invalid() throws Exception {
        when(googleTokenValidator.validate(anyString())).thenReturn(null);

        GoogleLoginRequest request = new GoogleLoginRequest("invalid-google-token");

        mockMvc.perform(post("/auth/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
