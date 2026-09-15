package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Profile;

import java.util.Optional;
import java.util.UUID;

public interface AllProfiles {

    Profile save(Profile profile);

    Optional<Profile> byId(UUID id);
}
