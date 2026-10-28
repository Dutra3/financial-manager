package gd.software.financial_manager.infrastructure.controllers.asset;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.usecase.asset.CreateAssetTransaction;
import gd.software.financial_manager.domain.usecase.asset.FetchAssetTransactions;
import gd.software.financial_manager.infrastructure.converts.AssetTransactionToDTO;
import gd.software.financial_manager.infrastructure.converts.DtoToAssetTransaction;
import gd.software.financial_manager.infrastructure.dtos.AssetTransactionDTO;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asset-transactions")
public class AssetTransactionEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(AssetTransactionEndpoints.class);

    @Autowired
    private CreateAssetTransaction createAssetTransaction;

    @Autowired
    private FetchAssetTransactions fetchAssetTransactions;

    @PostMapping
    public ResponseEntity<AssetTransactionDTO> save(@Valid @RequestBody AssetTransactionDTO assetTransactionDTO) {
        AssetTransaction assetTransaction = DtoToAssetTransaction.convert(assetTransactionDTO);
        assetTransaction.setUserId(SecurityUtils.resolveUserId(assetTransaction.userId()));
        AssetTransaction savedAssetTransaction = createAssetTransaction.use(assetTransaction);

        logger.info("Saved asset transaction {} for user {}.", savedAssetTransaction.id(), savedAssetTransaction.userId());

        return ResponseEntity.status(HttpStatus.CREATED).body(AssetTransactionToDTO.convert(savedAssetTransaction));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AssetTransactionDTO>> fetchAllByUser(@PathVariable UUID userId) {
        SecurityUtils.requireSameUser(userId);
        List<AssetTransaction> assetTransactions = fetchAssetTransactions.allBy(userId);
        logger.info("Get all asset transactions for user {}.", userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                assetTransactions.stream().map(AssetTransactionToDTO::convert).toList());
    }
}
