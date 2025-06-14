package gd.software.financial_manager.infrastructure.controllers.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(WalletEndpoints.class);

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> fetchWallet(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
