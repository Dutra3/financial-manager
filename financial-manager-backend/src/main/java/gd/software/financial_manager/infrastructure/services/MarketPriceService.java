package gd.software.financial_manager.infrastructure.services;

import gd.software.financial_manager.infrastructure.persistence.relational.AssetRow;
import gd.software.financial_manager.infrastructure.persistence.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarketPriceService {

    private static final Logger logger = LoggerFactory.getLogger(MarketPriceService.class);
    private static final String BRAPI_URL = "https://brapi.dev/api/quote/";

    private final AssetRepository assetRepository;
    private final RestTemplate restTemplate;
    private final String token;

    public MarketPriceService(AssetRepository assetRepository,
                              @Value("${market.brapi-token:}") String token) {
        this(assetRepository, token, new RestTemplate());
    }

    MarketPriceService(AssetRepository assetRepository, String token, RestTemplate restTemplate) {
        this.assetRepository = assetRepository;
        this.restTemplate = restTemplate;
        this.token = token;
    }

    @Scheduled(initialDelayString = "${market.refresh-initial-delay-ms:60000}",
            fixedDelayString = "${market.refresh-interval-ms:1800000}")
    public int refreshPrices() {
        List<AssetRow> assets = assetRepository.findAll().stream()
                .filter(a -> a.getTicker() != null && !a.getTicker().isBlank())
                .toList();
        if (assets.isEmpty()) {
            return 0;
        }

        Map<String, AssetRow> byTicker = assets.stream()
                .collect(Collectors.toMap(AssetRow::getTicker, a -> a, (a, b) -> a));

        String url = BRAPI_URL + String.join(",", byTicker.keySet())
                + (token != null && !token.isBlank() ? "?token=" + token : "");

        try {
            QuoteResponse response = restTemplate.getForObject(url, QuoteResponse.class);
            if (response == null || response.results() == null) {
                logger.warn("Market price refresh returned no results.");
                return 0;
            }

            int updated = 0;
            for (QuoteResult quote : response.results()) {
                AssetRow asset = byTicker.get(quote.symbol());
                if (asset != null && quote.regularMarketPrice() != null) {
                    asset.setPrice(BigDecimal.valueOf(quote.regularMarketPrice()));
                    assetRepository.save(asset);
                    updated++;
                }
            }
            logger.info("Refreshed prices for {} assets.", updated);
            return updated;
        } catch (Exception e) {
            logger.warn("Market price refresh failed: {}", e.getMessage());
            return 0;
        }
    }

    public record QuoteResponse(List<QuoteResult> results) {}
    public record QuoteResult(String symbol, Double regularMarketPrice) {}
}
