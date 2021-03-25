package nd.ermakov.wcapp.config;

import nd.ermakov.wcapp.dataload.WebLoadClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    public WebLoadClient weatherLoadClient() {
        return new WebLoadClient(WebClient.create("http://api.weatherapi.com/v1"));
    }

    @Bean
    public DateTimeFormatter currencyRequestDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @Bean
    public DateTimeFormatter currencyResponseDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    @Bean
    public DateTimeFormatter weatherRequestDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Bean
    public String weatherApiKey() {
        return "ff6dac694e8d482d9f1174924200912";
    }
}
