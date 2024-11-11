package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.bond.CreateBondTransaction;
import gd.software.financial_manager.domain.usecase.bond.FetchBond;
import gd.software.financial_manager.domain.usecase.collections.AllBondTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class BondConfig {

    @Bean
    @Autowired
    public CreateBondTransaction createBond(AllBondTransactions allBondTransactions) {
        return new CreateBondTransaction(allBondTransactions);
    }

    @Bean
    @Autowired
    public FetchBond fetchBond(AllBonds allBonds) {
        return new FetchBond(allBonds);
    }
}
