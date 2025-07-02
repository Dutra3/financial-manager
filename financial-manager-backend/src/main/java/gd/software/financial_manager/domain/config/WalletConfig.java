package gd.software.financial_manager.domain.config;

import gd.software.financial_manager.domain.usecase.collections.AllWallets;
import gd.software.financial_manager.domain.usecase.wallet.FetchWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletConfig {

    @Bean
    @Autowired
    public FetchWallet fetchWallet(AllWallets allWallets) {
        return new FetchWallet(allWallets);
    }
}
