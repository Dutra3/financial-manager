package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllReitTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllReits;
import gd.software.financial_manager.domain.usecase.reit.CreateReitTransaction;
import gd.software.financial_manager.domain.usecase.reit.FetchReit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReitConfig {

    @Bean
    @Autowired
    public CreateReitTransaction createReitTransaction(AllReitTransactions allReitTransactions) {
        return new CreateReitTransaction(allReitTransactions);
    }

    @Bean
    @Autowired
    public FetchReit fetchReit(AllReits allReits) {
        return new FetchReit(allReits);
    }
}
