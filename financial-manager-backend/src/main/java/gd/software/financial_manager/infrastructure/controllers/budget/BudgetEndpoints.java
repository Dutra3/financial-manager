package gd.software.financial_manager.infrastructure.controllers.budget;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.model.BudgetAlert;
import gd.software.financial_manager.domain.usecase.budget.BudgetAlerts;
import gd.software.financial_manager.domain.usecase.budget.CreateBudget;
import gd.software.financial_manager.domain.usecase.budget.DeleteBudget;
import gd.software.financial_manager.domain.usecase.budget.FetchBudgets;
import gd.software.financial_manager.infrastructure.converts.BudgetToDTO;
import gd.software.financial_manager.infrastructure.converts.DtoToBudget;
import gd.software.financial_manager.infrastructure.dtos.BudgetAlertDTO;
import gd.software.financial_manager.infrastructure.dtos.BudgetDTO;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budgets")
public class BudgetEndpoints {

    @Autowired
    private CreateBudget createBudget;

    @Autowired
    private FetchBudgets fetchBudgets;

    @Autowired
    private DeleteBudget deleteBudget;

    @Autowired
    private BudgetAlerts budgetAlerts;

    @Autowired
    private BudgetRepository budgetRepository;

    @PostMapping
    public ResponseEntity<BudgetDTO> save(@RequestBody BudgetDTO budgetDTO) {
        Budget budget = DtoToBudget.convert(budgetDTO);
        Budget savedBudget = createBudget.use(budget);
        BudgetRow row = budgetRepository.findById(savedBudget.id()).orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(BudgetToDTO.convert(row));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BudgetDTO>> fetchBudgets(@PathVariable UUID userId) {
        List<BudgetRow> rows = budgetRepository.findByUserId(userId);
        return ResponseEntity.ok(rows.stream().map(BudgetToDTO::convert).toList());
    }

    @GetMapping("/{userId}/{month}/{year}")
    public ResponseEntity<List<BudgetDTO>> fetchBudgetsByMonth(@PathVariable UUID userId,
                                                               @PathVariable Integer month,
                                                               @PathVariable Integer year) {
        List<BudgetRow> rows = budgetRepository.findByUserIdAndMonthAndYear(userId, month, year);
        return ResponseEntity.ok(rows.stream().map(BudgetToDTO::convert).toList());
    }

    @GetMapping("/alerts/{userId}/{month}/{year}")
    public ResponseEntity<List<BudgetAlertDTO>> fetchAlerts(@PathVariable UUID userId,
                                                            @PathVariable Integer month,
                                                            @PathVariable Integer year) {
        List<BudgetAlert> alerts = budgetAlerts.forUserAndMonth(userId, month, year);
        return ResponseEntity.ok(alerts.stream().map(BudgetAlertDTO::from).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id) {
        deleteBudget.use(id);
        return ResponseEntity.noContent().build();
    }
}
