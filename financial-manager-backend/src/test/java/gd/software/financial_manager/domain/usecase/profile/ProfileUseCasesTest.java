package gd.software.financial_manager.domain.usecase.profile;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileUseCasesTest {

    @Mock
    private AllProfiles allProfiles;

    @InjectMocks
    private CreateProfile createProfile;

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
}
