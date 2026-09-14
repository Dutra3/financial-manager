package gd.software.financial_manager.infrastructure.controllers.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.profile.CreateProfile;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateProfile createProfile;

    private static final UUID PROFILE_ID = UUID.randomUUID();

    @Test
    void should_create_profile_and_return_201() throws Exception {
        ProfileDTO request = new ProfileDTO(null, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        Profile saved = new Profile(PROFILE_ID, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        when(createProfile.use(any(Profile.class))).thenReturn(saved);

        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Gabriel Dutra"))
                .andExpect(jsonPath("$.profession").value("Developer"));
    }
}
