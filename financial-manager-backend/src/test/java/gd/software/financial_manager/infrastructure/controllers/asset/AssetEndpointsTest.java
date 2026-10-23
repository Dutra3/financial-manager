package gd.software.financial_manager.infrastructure.controllers.asset;

import gd.software.financial_manager.domain.model.Asset;
import gd.software.financial_manager.domain.model.AssetType;
import gd.software.financial_manager.domain.usecase.asset.FetchAssets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AssetEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FetchAssets fetchAssets;

    private static final UUID ASSET_ID = UUID.randomUUID();

    private Asset sampleAsset() {
        return new Asset(ASSET_ID, "PETR4", "Petrobras", AssetType.STOCK, "Oil company",
                "Ação", "Energy", new BigDecimal("38.00"), null, new BigDecimal("5.5"),
                new BigDecimal("8.2"), new BigDecimal("1.1"), new BigDecimal("2.0"), false, false);
    }

    @Test
    void should_fetch_all_assets() throws Exception {
        when(fetchAssets.all()).thenReturn(List.of(sampleAsset()));

        mockMvc.perform(get("/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("PETR4"))
                .andExpect(jsonPath("$[0].assetType").value("STOCK"));
    }

    @Test
    void should_fetch_assets_by_type() throws Exception {
        when(fetchAssets.allByType(AssetType.REIT)).thenReturn(List.of(
                new Asset(ASSET_ID, "HGLG11", "CSHG Logística", AssetType.REIT, "FII",
                        "Tijolo", "Logística", new BigDecimal("160.00"), null, null, null, null, null, null, null)));

        mockMvc.perform(get("/assets").param("type", "REIT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("HGLG11"))
                .andExpect(jsonPath("$[0].assetType").value("REIT"));
    }

    @Test
    void should_fetch_asset_by_id() throws Exception {
        when(fetchAssets.by(ASSET_ID)).thenReturn(sampleAsset());

        mockMvc.perform(get("/assets/" + ASSET_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Petrobras"));
    }
}
