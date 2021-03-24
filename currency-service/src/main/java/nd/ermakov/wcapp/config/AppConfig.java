package nd.ermakov.wcapp.config;

import nd.ermakov.wcapp.dataload.WebLoadClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    public String currencyName() {
        return "US Dollar";
    }

    @Bean
    public WebLoadClient currencyLoadClient() {
        return new WebLoadClient(WebClient.create("http://www.cbr.ru"));
    }

    @Bean
    public DateTimeFormatter currencyRequestDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @Bean
    public DateTimeFormatter currencyResponseDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

}
