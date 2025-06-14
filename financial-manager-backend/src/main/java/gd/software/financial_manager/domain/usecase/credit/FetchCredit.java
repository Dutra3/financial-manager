package gd.software.financial_manager.domain.usecase.credit;

import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchCredit {

    private static final Logger logger = LoggerFactory.getLogger(FetchCredit.class);

    private final AllTransactions allTransactions;

    public FetchCredit(AllTransactions allTransactions) {
        this.allTransactions = allTransactions;
    }

    public List<Installment> all(UUID id) {
        return allTransactions.byUserIdAndType(id, CategoryType.CREDIT);
    }
}
