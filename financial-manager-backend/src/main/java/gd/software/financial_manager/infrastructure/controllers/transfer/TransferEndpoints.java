package gd.software.financial_manager.infrastructure.controllers.transfer;

import gd.software.financial_manager.domain.usecase.transaction.TransferBetweenAccounts;
import gd.software.financial_manager.infrastructure.dtos.TransferDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
public class TransferEndpoints {

    @Autowired
    private TransferBetweenAccounts transferBetweenAccounts;

    @PostMapping
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferDTO transferDTO) {
        transferBetweenAccounts.use(transferDTO.fromAccountId(), transferDTO.toAccountId(),
                transferDTO.amount(), transferDTO.date(), transferDTO.description());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
