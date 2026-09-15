package gd.software.financial_manager.infrastructure.controllers.goal;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.domain.usecase.goal.CreateGoal;
import gd.software.financial_manager.domain.usecase.goal.CurrentBalance;
import gd.software.financial_manager.domain.usecase.goal.DeleteGoal;
import gd.software.financial_manager.domain.usecase.goal.FetchGoals;
import gd.software.financial_manager.infrastructure.converts.DtoToGoal;
import gd.software.financial_manager.infrastructure.converts.GoalToDTO;
import gd.software.financial_manager.infrastructure.dtos.GoalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/goals")
public class GoalEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(GoalEndpoints.class);

    @Autowired
    private CreateGoal createGoal;

    @Autowired
    private FetchGoals fetchGoals;

    @Autowired
    private DeleteGoal deleteGoal;

    @Autowired
    private CurrentBalance currentBalance;

    @PostMapping
    public ResponseEntity<GoalDTO> save(@RequestBody GoalDTO goalDTO) {
        Goal goal = DtoToGoal.convert(goalDTO);
        Goal savedGoal = createGoal.use(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(GoalToDTO.convert(savedGoal));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GoalDTO>> fetchGoals(@PathVariable UUID userId) {
        List<Goal> goals = fetchGoals.byUserId(userId);
        return ResponseEntity.ok(goals.stream().map(GoalToDTO::convert).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable UUID id) {
        deleteGoal.use(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<BigDecimal> fetchCurrentBalance(@PathVariable UUID userId) {
        BigDecimal balance = currentBalance.forUser(userId);
        return ResponseEntity.ok(balance);
    }
}
