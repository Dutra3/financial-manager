package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.bond.CreateBond;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class BondConfig {

    @Bean
    @Autowired
    public CreateBond createBond(AllBonds allBonds) {
        return new CreateBond(allBonds);
    }
}
