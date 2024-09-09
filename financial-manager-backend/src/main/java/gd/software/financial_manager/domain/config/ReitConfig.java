package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllReits;
import gd.software.financial_manager.domain.usecase.reit.CreateReit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ReitConfig {

    @Bean
    @Autowired
    public CreateReit createReit(AllReits allReits) {
        return new CreateReit(allReits);
    }
}
