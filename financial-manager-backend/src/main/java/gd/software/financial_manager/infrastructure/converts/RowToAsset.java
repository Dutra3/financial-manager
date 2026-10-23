package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;

import java.util.List;

public class RowToAsset {

    public static Asset convert(AssetRow row) {
        return new Asset(row.getId(), row.getTicker(), row.getName(),
                AssetType.valueOf(row.getAssetType().name()), row.getDescription(), row.getType(),
                row.getIndustrySegment(), row.getPrice(), row.getTagAlong(), row.getPeRatio(),
                row.getDividendYield(), row.getPbRatio(), row.getLastDividend(),
                row.getIsBesst(), row.getIsNewMarket());
    }

    public static List<Asset> convert(List<AssetRow> rows) {
        return rows.stream().map(RowToAsset::convert).toList();
    }
}
