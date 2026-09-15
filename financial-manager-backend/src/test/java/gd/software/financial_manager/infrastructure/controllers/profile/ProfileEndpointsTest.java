package gd.software.financial_manager.infrastructure.controllers.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.profile.CreateProfile;
import gd.software.financial_manager.domain.usecase.profile.FetchProfile;
import gd.software.financial_manager.domain.usecase.profile.UpdateProfile;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;
import jakarta.persistence.EntityNotFoundException;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockBean
    private FetchProfile fetchProfile;

    @MockBean
    private UpdateProfile updateProfile;

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

    @Test
    void should_fetch_profile_by_id_and_return_200() throws Exception {
        Profile profile = new Profile(PROFILE_ID, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        when(fetchProfile.by(PROFILE_ID)).thenReturn(profile);

        mockMvc.perform(get("/profiles/{id}", PROFILE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gabriel Dutra"))
                .andExpect(jsonPath("$.profession").value("Developer"))
                .andExpect(jsonPath("$.salary").value(5500.00));
    }

    @Test
    void should_return_404_when_profile_not_found() throws Exception {
        UUID missingId = UUID.randomUUID();
        when(fetchProfile.by(missingId)).thenThrow(new EntityNotFoundException("Profile not found with id " + missingId));

        mockMvc.perform(get("/profiles/{id}", missingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"));
    }

    @Test
    void should_update_profile_and_return_200() throws Exception {
        ProfileDTO request = new ProfileDTO(null, "Updated Name", "Updated Job",
                new BigDecimal("6000.00"), 10, new BigDecimal("2000.00"), new BigDecimal("8000.00"));
        Profile updated = new Profile(PROFILE_ID, "Updated Name", "Updated Job",
                new BigDecimal("6000.00"), 10, new BigDecimal("2000.00"), new BigDecimal("8000.00"));
        when(updateProfile.use(eq(PROFILE_ID), any(Profile.class))).thenReturn(updated);

        mockMvc.perform(put("/profiles/{id}", PROFILE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.profession").value("Updated Job"))
                .andExpect(jsonPath("$.salary").value(6000.00));
    }

    @Test
    void should_return_404_when_updating_nonexistent_profile() throws Exception {
        UUID missingId = UUID.randomUUID();
        when(updateProfile.use(eq(missingId), any(Profile.class)))
                .thenThrow(new EntityNotFoundException("Profile not found with id " + missingId));

        mockMvc.perform(put("/profiles/{id}", missingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProfileDTO(null, "Name", "Job",
                                new BigDecimal("1000.00"), 5, new BigDecimal("100.00"), new BigDecimal("1000.00")))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"));
    }
}
