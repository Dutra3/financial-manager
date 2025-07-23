package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RowToProfileTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "Profile Name";
    private static final String PROFESSIONAL = "Software Developer";
    private static final BigDecimal NET_SALARY = new BigDecimal("4300.19");
    private static final Integer PAYDAY = 5;
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("4300.19");
    private static final BigDecimal FINANCIAL_GOAL = new BigDecimal("4300.19");

    @Test
    void should_convert() {
        ProfileRow row = ProfileRow.builder()
                .id(ID)
                .name(NAME)
                .profession(PROFESSIONAL)
                .netSalary(NET_SALARY)
                .payday(PAYDAY)
                .initialBalance(INITIAL_BALANCE)
                .financialGoal(FINANCIAL_GOAL)
                .user(null)
                .build();
        Profile profile = RowToProfile.convert(row);

        assertThat(profile).isNotNull();
        assertThat(profile.id()).isEqualTo(ID);
        assertThat(profile.name()).isEqualTo(NAME);
        assertThat(profile.profession()).isEqualTo(PROFESSIONAL);
        assertThat(profile.netSalary()).isEqualTo(NET_SALARY);
        assertThat(profile.payday()).isEqualTo(PAYDAY);
        assertThat(profile.initialBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(profile.financialGoal()).isEqualTo(FINANCIAL_GOAL);
    }
}
