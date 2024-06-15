package gd.software.financial_manager.infrastructure.controllers.profile;

import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfileEndpoints {

    @PostMapping
    public ResponseEntity<ProfileDTO> save() {
        return ResponseEntity.ok(new ProfileDTO(null, null, null, null, null, null));
    }
}
