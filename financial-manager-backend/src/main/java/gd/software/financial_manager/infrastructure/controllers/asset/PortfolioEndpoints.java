package gd.software.financial_manager.infrastructure.controllers.asset;

import gd.software.financial_manager.domain.model.PortfolioPosition;
import gd.software.financial_manager.domain.usecase.investment.FetchPortfolio;
import gd.software.financial_manager.domain.usecase.investment.FetchPortfolioHistory;
import gd.software.financial_manager.infrastructure.dtos.PortfolioHistoryPoint;
import gd.software.financial_manager.infrastructure.converts.PositionToResponse;
import gd.software.financial_manager.infrastructure.dtos.AllocationResponse;
import gd.software.financial_manager.infrastructure.dtos.PositionResponse;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
public class PortfolioEndpoints {

    @Autowired
    private FetchPortfolio fetchPortfolio;

    @Autowired
    private FetchPortfolioHistory fetchPortfolioHistory;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PositionResponse>> fetchPortfolio(@PathVariable UUID userId) {
        SecurityUtils.requireSameUser(userId);
        List<PortfolioPosition> positions = fetchPortfolio.byUser(userId);
        return ResponseEntity.ok(positions.stream().map(PositionToResponse::convert).toList());
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<PortfolioHistoryPoint>> fetchHistory(@PathVariable UUID userId) {
        SecurityUtils.requireSameUser(userId);
        var history = fetchPortfolioHistory.byUser(userId);
        List<PortfolioHistoryPoint> response = history.entrySet().stream()
                .map(e -> new PortfolioHistoryPoint(e.getKey().toString(), e.getValue()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/allocation")
    public ResponseEntity<List<AllocationResponse>> fetchAllocation(@PathVariable UUID userId) {
        SecurityUtils.requireSameUser(userId);
        var byType = fetchPortfolio.allocationByType(userId);
        java.math.BigDecimal total = byType.values().stream()
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        List<AllocationResponse> response = byType.entrySet().stream()
                .map(e -> new AllocationResponse(e.getKey(), e.getValue(),
                        total.signum() > 0
                                ? e.getValue().divide(total, 4, java.math.RoundingMode.HALF_UP)
                                        .multiply(new java.math.BigDecimal("100"))
                                : java.math.BigDecimal.ZERO))
                .toList();

        return ResponseEntity.ok(response);
    }
}
