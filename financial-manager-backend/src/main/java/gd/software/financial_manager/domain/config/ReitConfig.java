package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllReits;
import gd.software.financial_manager.domain.usecase.reit.CreateReitTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ReitConfig {

    @Bean
    @Autowired
    public CreateReitTransaction createReit(AllReits allReits) {
        return new CreateReitTransaction(allReits);
    }
}
