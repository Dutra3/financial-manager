package gd.software.financial_manager.infrastructure.controllers.dashboard;

import gd.software.financial_manager.infrastructure.dtos.DebitResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DashboardIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void should_fetch_debits_and_return_empty_list_when_no_transactions() {
        UUID randomUserId = UUID.randomUUID();

        ResponseEntity<DebitResponse[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/dashboards/debits/" + randomUserId, DebitResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void should_fetch_credits_and_return_empty_list_when_no_transactions() {
        UUID randomUserId = UUID.randomUUID();

        ResponseEntity<DebitResponse[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/dashboards/credits/" + randomUserId, DebitResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }
}
