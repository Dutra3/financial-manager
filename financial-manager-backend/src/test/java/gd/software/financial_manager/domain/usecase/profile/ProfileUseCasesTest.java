package gd.software.financial_manager.domain.usecase.profile;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileUseCasesTest {

    @Mock
    private AllProfiles allProfiles;

    @InjectMocks
    private CreateProfile createProfile;

    @InjectMocks
    private FetchProfile fetchProfile;

    @InjectMocks
    private UpdateProfile updateProfile;

    private static final UUID PROFILE_ID = UUID.randomUUID();

    @Test
    void createProfile_should_delegate_to_allProfiles_save() {
        Profile input = new Profile(null, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        Profile saved = new Profile(PROFILE_ID, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        when(allProfiles.save(input)).thenReturn(saved);

        Profile result = createProfile.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allProfiles).save(input);
    }

    @Test
    void fetchProfile_by_should_return_profile_when_found() {
        Profile profile = new Profile(PROFILE_ID, "Gabriel Dutra", "Developer",
                new BigDecimal("5500.00"), 7, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        when(allProfiles.byId(PROFILE_ID)).thenReturn(Optional.of(profile));

        Profile result = fetchProfile.by(PROFILE_ID);

        assertThat(result).isEqualTo(profile);
        verify(allProfiles).byId(PROFILE_ID);
    }

    @Test
    void fetchProfile_by_should_throw_when_not_found() {
        when(allProfiles.byId(PROFILE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchProfile.by(PROFILE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Profile not found");
    }

    @Test
    void updateProfile_should_update_when_profile_exists() {
        Profile existing = new Profile(PROFILE_ID, "Old Name", "Old Job",
                new BigDecimal("3000.00"), 5, new BigDecimal("500.00"), new BigDecimal("5000.00"));
        Profile input = new Profile(null, "New Name", "New Job",
                new BigDecimal("5500.00"), 10, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        Profile updated = new Profile(PROFILE_ID, "New Name", "New Job",
                new BigDecimal("5500.00"), 10, new BigDecimal("1200.00"), new BigDecimal("10000.00"));
        when(allProfiles.byId(PROFILE_ID)).thenReturn(Optional.of(existing));
        when(allProfiles.save(any(Profile.class))).thenReturn(updated);

        Profile result = updateProfile.use(PROFILE_ID, input);

        assertThat(result.name()).isEqualTo("New Name");
        assertThat(result.profession()).isEqualTo("New Job");
        assertThat(result.netSalary()).isEqualByComparingTo("5500.00");
        verify(allProfiles).save(any(Profile.class));
    }

    @Test
    void updateProfile_should_throw_when_not_found() {
        Profile input = new Profile(null, "Name", "Job",
                new BigDecimal("1000.00"), 5, new BigDecimal("100.00"), new BigDecimal("1000.00"));
        when(allProfiles.byId(PROFILE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateProfile.use(PROFILE_ID, input))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Profile not found");
    }
}
