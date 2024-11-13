package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllEtfTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllEtfs;
import gd.software.financial_manager.domain.usecase.etf.CreateEtfTransaction;
import gd.software.financial_manager.domain.usecase.etf.FetchEtf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class EtfConfig {

    @Bean
    @Autowired
    public CreateEtfTransaction createEtfTransaction(AllEtfTransactions allEtfTransactions) {
        return new CreateEtfTransaction(allEtfTransactions);
    }

    @Bean
    @Autowired
    public FetchEtf fetchEtf(AllEtfs allEtfs) {
        return new FetchEtf(allEtfs);
    }
}
