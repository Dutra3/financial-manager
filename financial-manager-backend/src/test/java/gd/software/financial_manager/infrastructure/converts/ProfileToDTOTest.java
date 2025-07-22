package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileToDTOTest {

    private static final UUID UUID = java.util.UUID.randomUUID();
    private static final String NAME = "User Namer";
    private static final String PROFESSION = "Software Developer";
    private static final BigDecimal SALARY = new BigDecimal("7650.00");
    private static final Integer PAYDAY = 5;
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("250.00");
    private static final BigDecimal FINANCIAL_GOAL = new BigDecimal("1000000.00");

    @Test
    void should_convert() {
        Profile profile = new Profile(UUID, NAME, PROFESSION, SALARY, PAYDAY, INITIAL_BALANCE, FINANCIAL_GOAL);
        ProfileDTO dto = ProfileToDTO.convert(profile);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(UUID);
        assertThat(dto.name()).isEqualTo(NAME);
        assertThat(dto.profession()).isEqualTo(PROFESSION);
        assertThat(dto.salary()).isEqualTo(SALARY);
        assertThat(dto.payDay()).isEqualTo(PAYDAY);
        assertThat(dto.initialBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(dto.financialGoal()).isEqualTo(FINANCIAL_GOAL);
    }
}
