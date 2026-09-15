package gd.software.financial_manager.domain.usecase.profile;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProfile {

    private static final Logger logger = LoggerFactory.getLogger(UpdateProfile.class);

    private final AllProfiles allProfiles;

    public UpdateProfile(AllProfiles allProfiles) {
        this.allProfiles = allProfiles;
    }

    public Profile use(UUID id, Profile profile) {
        logger.info("Update profile with id {}.", id);

        allProfiles.byId(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with id " + id));

        Profile updated = new Profile(id, profile.name(), profile.profession(), profile.netSalary(),
                profile.payday(), profile.initialBalance(), profile.financialGoal());

        return allProfiles.save(updated);
    }
}
