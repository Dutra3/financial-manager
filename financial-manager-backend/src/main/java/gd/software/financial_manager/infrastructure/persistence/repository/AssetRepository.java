package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTypeRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<AssetRow, UUID> {

    List<AssetRow> findAllByAssetType(AssetTypeRow assetType);

    Optional<AssetRow> findByTicker(String ticker);
}
