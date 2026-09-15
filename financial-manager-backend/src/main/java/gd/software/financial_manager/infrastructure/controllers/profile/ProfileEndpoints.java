package gd.software.financial_manager.infrastructure.controllers.profile;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.usecase.profile.CreateProfile;
import gd.software.financial_manager.domain.usecase.profile.FetchProfile;
import gd.software.financial_manager.domain.usecase.profile.UpdateProfile;
import gd.software.financial_manager.infrastructure.converts.DtoToProfile;
import gd.software.financial_manager.infrastructure.converts.ProfileToDTO;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profiles")
public class ProfileEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(ProfileEndpoints.class);

    @Autowired
    private CreateProfile createProfile;

    @Autowired
    private FetchProfile fetchProfile;

    @Autowired
    private UpdateProfile updateProfile;

    @PostMapping
    public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDTO) {
        Profile profile = DtoToProfile.convert(profileDTO);
        Profile savedProfile = createProfile.use(profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(ProfileToDTO.convert(savedProfile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> fetchProfile(@PathVariable UUID id) {
        Profile profile = fetchProfile.by(id);
        return ResponseEntity.ok(ProfileToDTO.convert(profile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable UUID id, @RequestBody ProfileDTO profileDTO) {
        Profile profile = DtoToProfile.convert(profileDTO);
        Profile updatedProfile = updateProfile.use(id, profile);
        return ResponseEntity.ok(ProfileToDTO.convert(updatedProfile));
    }
}
