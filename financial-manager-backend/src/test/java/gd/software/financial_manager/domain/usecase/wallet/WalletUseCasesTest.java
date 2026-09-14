package gd.software.financial_manager.domain.usecase.wallet;

import gd.software.financial_manager.domain.model.Wallet;
import gd.software.financial_manager.domain.usecase.collections.AllWallets;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletUseCasesTest {

    @Mock
    private AllWallets allWallets;

    @InjectMocks
    private FetchWallet fetchWallet;

    private static final UUID WALLET_ID = UUID.randomUUID();

    @Test
    void fetchWallet_use_should_return_wallet_when_found() {
        Wallet wallet = new Wallet(WALLET_ID, new BigDecimal("9999.00"), List.of(), List.of(), List.of());
        when(allWallets.by(WALLET_ID)).thenReturn(Optional.of(wallet));

        Wallet result = fetchWallet.use(WALLET_ID);

        assertThat(result).isEqualTo(wallet);
        verify(allWallets).by(WALLET_ID);
    }

    @Test
    void fetchWallet_use_should_throw_EntityNotFoundException_when_not_found() {
        when(allWallets.by(WALLET_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchWallet.use(WALLET_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
