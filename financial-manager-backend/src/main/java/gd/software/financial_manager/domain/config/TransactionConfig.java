package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import gd.software.financial_manager.domain.usecase.transaction.CreateTransaction;
import gd.software.financial_manager.domain.usecase.transaction.FetchTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {

    @Bean
    @Autowired
    public CreateTransaction createTransaction(AllTransactions allTransactions, AllCategories allCategories) {
        return new CreateTransaction(allTransactions, allCategories);
    }

    @Bean
    @Autowired
    public FetchTransaction fetchTransaction(AllTransactions allTransactions) {
        return new FetchTransaction(allTransactions);
    }
}
