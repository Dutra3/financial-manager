package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileToRowTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Profile Name";
    private static final String PROFESSIONAL = "Software Developer";
    private static final BigDecimal NET_SALARY = new BigDecimal("4300.19");
    private static final Integer PAYDAY = 5;
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("4300.19");
    private static final BigDecimal FINANCIAL_GOAL = new BigDecimal("4300.19");

    @Test
    void should_convert() {
        Profile profile = new Profile(ID, NAME, PROFESSIONAL, NET_SALARY, PAYDAY, INITIAL_BALANCE, FINANCIAL_GOAL);
        ProfileRow row = ProfileToRow.convert(profile);

        assertThat(row).isNotNull();
        assertThat(row.getId()).isEqualTo(ID);
        assertThat(row.getName()).isEqualTo(NAME);
        assertThat(row.getProfession()).isEqualTo(PROFESSIONAL);
        assertThat(row.getNetSalary()).isEqualTo(NET_SALARY);
        assertThat(row.getPayday()).isEqualTo(PAYDAY);
        assertThat(row.getInitialBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(row.getFinancialGoal()).isEqualTo(FINANCIAL_GOAL);
    }
}
