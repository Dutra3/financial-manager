package gd.software.financial_manager.infrastructure.controllers.category;

import gd.software.financial_manager.infrastructure.dtos.CategoryData;
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
class CategoryIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/categories";
    }

    @Test
    void should_create_fetch_and_delete_category() {
        CategoryData request = new CategoryData(null, "Food", "DEBIT");

        ResponseEntity<CategoryData> createResponse = restTemplate.postForEntity(baseUrl(), request, CategoryData.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().name()).isEqualTo("Food");
        assertThat(createResponse.getBody().type()).isEqualTo("DEBIT");

        UUID createdId = createResponse.getBody().id();
        assertThat(createdId).isNotNull();

        ResponseEntity<CategoryData> fetchResponse = restTemplate.getForEntity(baseUrl() + "/" + createdId, CategoryData.class);
        assertThat(fetchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetchResponse.getBody()).isNotNull();
        assertThat(fetchResponse.getBody().name()).isEqualTo("Food");

        CategoryData[] allCategories = restTemplate.getForObject(baseUrl(), CategoryData[].class);
        assertThat(allCategories).isNotNull();
        assertThat(allCategories.length).isGreaterThanOrEqualTo(1);

        restTemplate.delete(baseUrl() + "/" + createdId);

        ResponseEntity<CategoryData> afterDelete = restTemplate.getForEntity(baseUrl() + "/" + createdId, CategoryData.class);
        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void should_create_multiple_categories_and_fetch_all() {
        CategoryData first = new CategoryData(null, "Transport", "CREDIT");
        CategoryData second = new CategoryData(null, "Entertainment", "DEBIT");

        restTemplate.postForEntity(baseUrl(), first, CategoryData.class);
        restTemplate.postForEntity(baseUrl(), second, CategoryData.class);

        CategoryData[] allCategories = restTemplate.getForObject(baseUrl(), CategoryData[].class);
        assertThat(allCategories).isNotNull();
        assertThat(allCategories.length).isGreaterThanOrEqualTo(2);
    }
}
