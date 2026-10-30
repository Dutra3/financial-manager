package gd.software.financial_manager.infrastructure.controllers.asset;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.usecase.asset.FetchAssets;
import gd.software.financial_manager.infrastructure.converts.AssetToResponse;
import gd.software.financial_manager.infrastructure.dtos.AssetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(AssetEndpoints.class);

    @Autowired
    private FetchAssets fetchAssets;

    @Autowired
    private gd.software.financial_manager.infrastructure.services.MarketPriceService marketPriceService;

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> fetchAsset(@PathVariable UUID id) throws Exception {
        Asset asset = fetchAssets.by(id);
        logger.info("Get asset {}.", asset.name());

        return ResponseEntity.status(HttpStatus.OK).body(AssetToResponse.convert(asset));
    }

    @PostMapping("/refresh-prices")
    public ResponseEntity<gd.software.financial_manager.infrastructure.dtos.PriceRefreshResponse> refreshPrices() {
        int updated = marketPriceService.refreshPrices();
        logger.info("Refreshed {} asset prices.", updated);
        return ResponseEntity.ok(new gd.software.financial_manager.infrastructure.dtos.PriceRefreshResponse(updated));
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> fetchAllAssets(@RequestParam(required = false) String type) {
        List<Asset> assets = type != null
                ? fetchAssets.allByType(AssetType.valueOf(type.toUpperCase()))
                : fetchAssets.all();
        logger.info("Get all assets{}.", type != null ? " with type " + type : "");

        return ResponseEntity.status(HttpStatus.OK).body(AssetToResponse.convert(assets));
    }
}
