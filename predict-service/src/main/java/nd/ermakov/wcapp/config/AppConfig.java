package nd.ermakov.wcapp.config;

import nd.ermakov.wcapp.dataload.WebLoadClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebLoadClient currencyServiceClient() {
        return new WebLoadClient(WebClient.create("http://localhost:8081/currency"));
    }

    @Bean
    public WebLoadClient weatherServiceClient() {
        return new WebLoadClient(WebClient.create("http://localhost:8082/weather"));
    }
}
