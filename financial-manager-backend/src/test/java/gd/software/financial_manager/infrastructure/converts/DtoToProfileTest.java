package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoToProfileTest {

    private static final UUID UUID = java.util.UUID.randomUUID();
    private static final String NAME = "User Namer";
    private static final String PROFESSION = "Software Developer";
    private static final BigDecimal SALARY = new BigDecimal("7650.00");
    private static final Integer PAYDAY = 5;
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("250.00");
    private static final BigDecimal FINANCIAL_GOAL = new BigDecimal("1000000.00");

    @Test
    void should_convert() {
        ProfileDTO dto = new ProfileDTO(UUID, NAME, PROFESSION, SALARY, PAYDAY, INITIAL_BALANCE, FINANCIAL_GOAL);
        Profile profile = DtoToProfile.convert(dto);

        assertThat(profile).isNotNull();
        assertThat(profile.id()).isEqualTo(UUID);
        assertThat(profile.name()).isEqualTo(NAME);
        assertThat(profile.profession()).isEqualTo(PROFESSION);
        assertThat(profile.netSalary()).isEqualTo(SALARY);
        assertThat(profile.payday()).isEqualTo(PAYDAY);
        assertThat(profile.initialBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(profile.financialGoal()).isEqualTo(FINANCIAL_GOAL);
    }
}
