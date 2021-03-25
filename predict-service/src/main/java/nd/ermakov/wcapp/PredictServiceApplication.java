package nd.ermakov.wcapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PredictServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PredictServiceApplication.class, args);
    }
}
