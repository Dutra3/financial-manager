package gd.software.financial_manager.domain.usecase.debit;

import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Debit;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchDebit {

    private static final Logger logger = LoggerFactory.getLogger(FetchDebit.class);

    private final AllTransactions allTransactions;

    public FetchDebit(AllTransactions allTransactions) {
        this.allTransactions = allTransactions;
    }

    public List<Debit> all(UUID id) {
        return allTransactions.byUserIdAndType(id, CategoryType.DEBIT);
    }
}
