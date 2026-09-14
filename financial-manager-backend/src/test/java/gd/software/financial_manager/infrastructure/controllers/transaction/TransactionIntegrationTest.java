package gd.software.financial_manager.infrastructure.controllers.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;
import gd.software.financial_manager.infrastructure.dtos.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String categoriesUrl() {
        return "http://localhost:" + port + "/api/categories";
    }

    private String transactionsUrl() {
        return "http://localhost:" + port + "/api/transactions";
    }

    @Test
    void should_fetch_transactions_by_user_and_return_empty_list_when_no_data() {
        UUID randomUserId = UUID.randomUUID();

        ResponseEntity<TransactionResponse[]> response = restTemplate.getForEntity(
                transactionsUrl() + "/" + randomUserId, TransactionResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void should_create_category_for_transaction_dependency() {
        CategoryData request = new CategoryData(null, "Salary", "CREDIT");

        ResponseEntity<CategoryData> response = restTemplate.postForEntity(categoriesUrl(), request, CategoryData.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Salary");
    }

    @Test
    void should_delete_nonexistent_transaction_without_error() {
        UUID randomId = UUID.randomUUID();

        restTemplate.delete(transactionsUrl() + "/" + randomId);
    }

    @Test
    void should_return_404_when_updating_nonexistent_transaction() throws Exception {
        UUID randomId = UUID.randomUUID();
        TransactionDTO request = new TransactionDTO(randomId, "Lunch", "Restaurant",
                new java.math.BigDecimal("50.00"), java.time.LocalDate.of(2025, 1, 10), UUID.randomUUID());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                transactionsUrl() + "/" + randomId, org.springframework.http.HttpMethod.PUT, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
