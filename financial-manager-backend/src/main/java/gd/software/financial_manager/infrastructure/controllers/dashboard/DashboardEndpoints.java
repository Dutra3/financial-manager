package gd.software.financial_manager.infrastructure.controllers.dashboard;

import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.usecase.credit.FetchCredit;
import gd.software.financial_manager.domain.usecase.debit.FetchDebit;
import gd.software.financial_manager.infrastructure.dtos.CreditResponse;
import gd.software.financial_manager.infrastructure.dtos.DebitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dashboards")
public class DashboardEndpoints {

    @Autowired
    private FetchDebit fetchDebit;

    @Autowired
    private FetchCredit fetchCredit;

    @GetMapping("/debits/{id}")
    public ResponseEntity<List<DebitResponse>> fetchAllDebits(@PathVariable UUID id) {
        List<Installment> debits = fetchDebit.all(id);
        List<DebitResponse> responses = debits.stream()
                .map(debit -> new DebitResponse(debit.amount()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/credits/{id}")
    public ResponseEntity<List<CreditResponse>> fetchAllCredits(@PathVariable UUID id) {
        List<Installment> debits = fetchCredit.all(id);
        List<CreditResponse> responses = debits.stream()
                .map(credit -> new CreditResponse(credit.amount()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
