package gd.software.financial_manager.domain.usecase.etf;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.model.EtfTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllEtfTransactions;
import gd.software.financial_manager.domain.usecase.collections.AllEtfs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtfUseCasesTest {

    @Mock
    private AllEtfTransactions allEtfTransactions;

    @Mock
    private AllEtfs allEtfs;

    @InjectMocks
    private CreateEtfTransaction createEtfTransaction;

    @InjectMocks
    private FetchEtf fetchEtf;

    private static final UUID ETF_ID = UUID.randomUUID();
    private static final UUID TRANSACTION_ID = UUID.randomUUID();

    @Test
    void createEtfTransaction_should_delegate_to_allEtfTransactions_save() {
        Etf etf = new Etf(ETF_ID, "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                new BigDecimal("80.00"));
        EtfTransaction input = new EtfTransaction(null, etf, new BigDecimal("20"),
                new BigDecimal("80.00"), LocalDate.of(2025, 2, 1));
        EtfTransaction saved = new EtfTransaction(TRANSACTION_ID, etf, new BigDecimal("20"),
                new BigDecimal("80.00"), LocalDate.of(2025, 2, 1));
        when(allEtfTransactions.save(input)).thenReturn(saved);

        EtfTransaction result = createEtfTransaction.use(input);

        assertThat(result).isEqualTo(saved);
        verify(allEtfTransactions).save(input);
    }

    @Test
    void fetchEtf_by_should_return_etf_when_found() throws Exception {
        Etf etf = new Etf(ETF_ID, "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                new BigDecimal("80.00"));
        when(allEtfs.by(ETF_ID)).thenReturn(Optional.of(etf));

        Etf result = fetchEtf.by(ETF_ID);

        assertThat(result).isEqualTo(etf);
        verify(allEtfs).by(ETF_ID);
    }

    @Test
    void fetchEtf_by_should_throw_when_not_found() {
        when(allEtfs.by(ETF_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fetchEtf.by(ETF_ID))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("cant_find_etf_with_id");
    }

    @Test
    void fetchEtf_all_should_delegate_to_allEtfs_all() {
        List<Etf> etfs = List.of(
                new Etf(ETF_ID, "IVVB11", "IVVB11", "S&P 500 ETF", "ETF", "International",
                        new BigDecimal("80.00")));
        when(allEtfs.all()).thenReturn(etfs);

        List<Etf> result = fetchEtf.all();

        assertThat(result).isEqualTo(etfs);
        verify(allEtfs).all();
    }
}
