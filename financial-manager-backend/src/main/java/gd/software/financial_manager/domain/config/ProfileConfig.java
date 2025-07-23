package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import gd.software.financial_manager.domain.usecase.profile.CreateProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfig {

    @Bean
    @Autowired
    public CreateProfile createProfile(AllProfiles allProfiles) {
        return new CreateProfile(allProfiles);
    }
}
