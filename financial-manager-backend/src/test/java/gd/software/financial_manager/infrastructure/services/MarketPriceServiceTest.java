package gd.software.financial_manager.infrastructure.services;

import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AssetTypeRow;
import gd.software.financial_manager.infrastructure.persistence.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketPriceServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private RestTemplate restTemplate;

    private MarketPriceService service() {
        return new MarketPriceService(assetRepository, "", restTemplate);
    }

    private AssetRow row(String ticker) {
        return AssetRow.builder()
                .id(UUID.randomUUID())
                .ticker(ticker)
                .name(ticker)
                .assetType(AssetTypeRow.STOCK)
                .price(new BigDecimal("10.00"))
                .build();
    }

    @Test
    void should_update_prices_from_brapi_quotes() {
        AssetRow petr = row("PETR4");
        AssetRow vale = row("VALE3");
        when(assetRepository.findAll()).thenReturn(List.of(petr, vale));
        when(restTemplate.getForObject(anyString(), eq(MarketPriceService.QuoteResponse.class)))
                .thenReturn(new MarketPriceService.QuoteResponse(List.of(
                        new MarketPriceService.QuoteResult("PETR4", 40.50),
                        new MarketPriceService.QuoteResult("VALE3", 65.10))));

        int updated = service().refreshPrices();

        assertThat(updated).isEqualTo(2);
        assertThat(petr.getPrice()).isEqualByComparingTo("40.50");
        assertThat(vale.getPrice()).isEqualByComparingTo("65.10");
        verify(assetRepository, times(2)).save(any(AssetRow.class));
    }

    @Test
    void should_skip_assets_without_ticker() {
        when(assetRepository.findAll()).thenReturn(List.of(row(null), row("")));

        assertThat(service().refreshPrices()).isZero();
        verify(restTemplate, never()).getForObject(anyString(), eq(MarketPriceService.QuoteResponse.class));
        verify(assetRepository, never()).save(any());
    }

    @Test
    void should_return_zero_when_api_fails() {
        when(assetRepository.findAll()).thenReturn(List.of(row("PETR4")));
        when(restTemplate.getForObject(anyString(), eq(MarketPriceService.QuoteResponse.class)))
                .thenThrow(new RuntimeException("connection refused"));

        assertThat(service().refreshPrices()).isZero();
        verify(assetRepository, never()).save(any());
    }
}
