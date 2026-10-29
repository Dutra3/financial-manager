package gd.software.financial_manager.infrastructure.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.infrastructure.dtos.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "ratelimit.auth.max-requests=3")
class AuthRateLimitFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_429_after_exceeding_auth_rate_limit() throws Exception {
        String body = objectMapper.writeValueAsString(new LoginRequest("a@b.com", "Password1!"));

        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isUnauthorized());
        }

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    void should_not_rate_limit_non_auth_endpoints() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(
                                    new gd.software.financial_manager.infrastructure.dtos.UserData(
                                            java.util.UUID.randomUUID(), "user" + i + "@test.com", "Password1!"))))
                    .andExpect(status().isCreated());
        }
    }
}
