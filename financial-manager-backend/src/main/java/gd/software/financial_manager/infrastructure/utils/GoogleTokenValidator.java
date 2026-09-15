package gd.software.financial_manager.infrastructure.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GoogleTokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenValidator.class);
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoogleUserInfo validate(String accessToken) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GOOGLE_USERINFO_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                logger.warn("Google token validation failed with status {}", response.statusCode());
                return null;
            }

            JsonNode json = objectMapper.readTree(response.body());
            String sub = json.path("sub").asText();
            String email = json.path("email").asText();

            if (sub.isEmpty() || email.isEmpty()) {
                logger.warn("Google token response missing sub or email");
                return null;
            }

            return new GoogleUserInfo(sub, email);
        } catch (Exception e) {
            logger.error("Error validating Google token", e);
            return null;
        }
    }

    public record GoogleUserInfo(String sub, String email) {}
}
