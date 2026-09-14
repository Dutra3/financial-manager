package gd.software.financial_manager;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialManagerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		setPropertyIfPresent("DB_USERNAME", dotenv);
		setPropertyIfPresent("DB_PASSWORD", dotenv);
		setPropertyIfPresent("DB_URL", dotenv);

		SpringApplication.run(FinancialManagerApplication.class, args);
	}

	private static void setPropertyIfPresent(String key, Dotenv dotenv) {
		String value = dotenv.get(key);
		if (value == null) {
			value = System.getenv(key);
		}
		if (value != null) {
			System.setProperty(key, value);
		}
	}

}
