package gd.software.financial_manager.domain.usecase.profile;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateProfile {

    private static final Logger logger = LoggerFactory.getLogger(CreateProfile.class);

    private final AllProfiles allProfiles;

    public CreateProfile(AllProfiles allProfiles) {
        this.allProfiles = allProfiles;
    }

    public Profile use(Profile profile) {
        logger.info("Create Profile with name {}", profile.name());

        return allProfiles.save(profile);
    }
}
