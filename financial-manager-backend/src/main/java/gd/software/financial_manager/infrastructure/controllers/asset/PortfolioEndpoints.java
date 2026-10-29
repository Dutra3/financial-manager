package gd.software.financial_manager.infrastructure.controllers.asset;

import gd.software.financial_manager.domain.model.PortfolioPosition;
import gd.software.financial_manager.domain.usecase.investment.FetchPortfolio;
import gd.software.financial_manager.infrastructure.converts.PositionToResponse;
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

    @GetMapping("/{userId}")
    public ResponseEntity<List<PositionResponse>> fetchPortfolio(@PathVariable UUID userId) {
        SecurityUtils.requireSameUser(userId);
        List<PortfolioPosition> positions = fetchPortfolio.byUser(userId);
        return ResponseEntity.ok(positions.stream().map(PositionToResponse::convert).toList());
    }
}
