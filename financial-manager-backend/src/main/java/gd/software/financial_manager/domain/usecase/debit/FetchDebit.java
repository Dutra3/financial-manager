package gd.software.financial_manager.domain.usecase.debit;

import gd.software.financial_manager.domain.model.Debit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FetchDebit {

    private static final Logger logger = LoggerFactory.getLogger(FetchDebit.class);

    public FetchDebit() {

    }

    public Debit all() {
        return null;
    }
}
