package gd.software.financial_manager.infrastructure.controllers.account;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.usecase.account.CreateAccount;
import gd.software.financial_manager.domain.usecase.account.DeleteAccount;
import gd.software.financial_manager.domain.usecase.account.FetchAccounts;
import gd.software.financial_manager.infrastructure.converts.AccountToDTO;
import gd.software.financial_manager.infrastructure.converts.DtoToAccount;
import gd.software.financial_manager.infrastructure.dtos.AccountDTO;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(AccountEndpoints.class);

    @Autowired
    private CreateAccount createAccount;

    @Autowired
    private FetchAccounts fetchAccounts;

    @Autowired
    private DeleteAccount deleteAccount;

    @PostMapping
    public ResponseEntity<AccountDTO> save(@Valid @RequestBody AccountDTO accountDTO) {
        Account account = DtoToAccount.convert(accountDTO);
        account.setUserId(SecurityUtils.currentUserId());
        Account saved = createAccount.use(account);
        logger.info("Created account {} for user {}.", saved.name(), saved.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountToDTO.convert(saved));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountDTO>> fetchAccounts(@PathVariable UUID userId) {
        SecurityUtils.requireSameUser(userId);
        List<Account> accounts = fetchAccounts.byUserId(userId);
        return ResponseEntity.ok(accounts.stream().map(AccountToDTO::convert).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        deleteAccount.use(id);
        return ResponseEntity.noContent().build();
    }
}
