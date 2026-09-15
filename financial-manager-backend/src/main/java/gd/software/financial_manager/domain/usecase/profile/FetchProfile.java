package gd.software.financial_manager.domain.usecase.profile;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FetchProfile {

    private static final Logger logger = LoggerFactory.getLogger(FetchProfile.class);

    private final AllProfiles allProfiles;

    public FetchProfile(AllProfiles allProfiles) {
        this.allProfiles = allProfiles;
    }

    public Profile by(UUID id) {
        logger.info("Fetch profile with id {}.", id);
        return allProfiles.byId(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with id " + id));
    }
}
