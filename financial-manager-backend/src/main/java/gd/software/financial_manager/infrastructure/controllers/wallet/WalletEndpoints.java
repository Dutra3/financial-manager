package gd.software.financial_manager.infrastructure.controllers.wallet;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.domain.usecase.wallet.FetchWallet;
import gd.software.financial_manager.infrastructure.converts.WalletToResponse;
import gd.software.financial_manager.infrastructure.dtos.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(WalletEndpoints.class);

    @Autowired
    private FetchWallet fetchWallet;

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> fetchWallet(@PathVariable UUID id) {
        Wallet wallet = fetchWallet.use(id);
        logger.info("Fetch wallet with id {}", wallet.id());

        return ResponseEntity.status(HttpStatus.OK).body(WalletToResponse.convert(wallet));
    }
}
