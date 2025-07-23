package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import gd.software.financial_manager.infrastructure.converts.ProfileToRow;
import gd.software.financial_manager.infrastructure.converts.RowToProfile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;
import gd.software.financial_manager.infrastructure.persistence.repository.ProfileRepository;

public class AllProfilesPersistent implements AllProfiles {

    private final ProfileRepository repository;

    public AllProfilesPersistent(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profile save(Profile profile) {
        ProfileRow row = ProfileToRow.convert(profile);

        ProfileRow savedProfile = repository.save(row);

        return RowToProfile.convert(savedProfile);
    }
}
