package gd.software.financial_manager.infrastructure.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleEntityNotFound_should_return_404_with_error_message() {
        EntityNotFoundException ex = new EntityNotFoundException("Wallet not found");

        ResponseEntity<Map<String, String>> response = handler.handleEntityNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("error", "Resource not found");
    }

    @Test
    void handleGenericException_should_return_500_with_exception_message() {
        Exception ex = new RuntimeException("Something went wrong");

        ResponseEntity<Map<String, String>> response = handler.handleGenericException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).containsEntry("error", "Something went wrong");
    }
}
