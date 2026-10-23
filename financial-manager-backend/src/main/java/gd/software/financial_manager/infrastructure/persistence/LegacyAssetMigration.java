package gd.software.financial_manager.infrastructure.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LegacyAssetMigration implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(LegacyAssetMigration.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        Integer assetCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM gd_asset", Integer.class);
        if (assetCount != null && assetCount > 0) {
            return;
        }

        int migrated = migrateStocks() + migrateSimple("gd_reit", "REIT")
                + migrateSimple("gd_etf", "ETF") + migrateBonds();

        if (migrated > 0) {
            logger.info("Migrated {} legacy assets into gd_asset.", migrated);
        }
    }

    private int migrateStocks() {
        return copy("""
                INSERT INTO gd_asset (id, ticker, name, asset_type, description, type, industry_segment,
                        price, tag_along, pe_ratio, dividend_yield, pb_ratio, last_dividend, is_besst, is_new_market)
                SELECT id, ticker, name, 'STOCK', description, type, industry_segment,
                        price, tag_along, pe_ratio, dividend_yield, pb_ratio, last_dividend, is_besst, is_new_market
                FROM gd_stock
                """, "gd_stock");
    }

    private int migrateBonds() {
        return copy("""
                INSERT INTO gd_asset (id, ticker, name, asset_type, description, type, industry_segment, price)
                SELECT id, NULL, name, 'BOND', description, type, industry_segment, price
                FROM gd_bond
                """, "gd_bond");
    }

    private int migrateSimple(String table, String assetType) {
        return copy(String.format("""
                INSERT INTO gd_asset (id, ticker, name, asset_type, description, type, industry_segment, price)
                SELECT id, ticker, name, '%s', description, type, industry_segment, price
                FROM %s
                """, assetType, table), table);
    }

    private int copy(String insertSql, String legacyTable) {
        try {
            return jdbcTemplate.update(insertSql);
        } catch (DataAccessException e) {
            logger.debug("Skipping legacy migration for {} ({}).", legacyTable, e.getMessage());
            return 0;
        }
    }
}
