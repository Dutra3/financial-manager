package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.usecase.collections.AllAssets;
import gd.software.financial_manager.infrastructure.converts.RowToAsset;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTypeRow;
import gd.software.financial_manager.infrastructure.persistence.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AllAssetsPersistent implements AllAssets {

    private static final Logger logger = LoggerFactory.getLogger(AllAssetsPersistent.class);

    @Autowired
    AssetRepository repository;

    @Override
    public Optional<Asset> by(UUID id) {
        logger.info("Find Asset with id {}.", id);
        return repository.findById(id).map(RowToAsset::convert);
    }

    @Override
    public List<Asset> all() {
        logger.info("Find all Assets.");
        return repository.findAll().stream().map(RowToAsset::convert).toList();
    }

    @Override
    public List<Asset> allByType(AssetType assetType) {
        logger.info("Find all Assets with type {}.", assetType);
        return repository.findAllByAssetType(AssetTypeRow.valueOf(assetType.name()))
                .stream().map(RowToAsset::convert).toList();
    }
}
