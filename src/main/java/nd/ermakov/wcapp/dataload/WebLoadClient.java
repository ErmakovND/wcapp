package nd.ermakov.wcapp.dataload;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

public class WebLoadClient {

    private WebClient webClient;

    public WebLoadClient(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public String get(String url, MultiValueMap<String, String> params) {
        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(url)
                                .queryParams(params)
                                .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
