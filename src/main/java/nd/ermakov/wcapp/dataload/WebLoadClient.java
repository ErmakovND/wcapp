package nd.ermakov.wcapp.dataload;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

public class WebLoadClient {

    private WebClient webClient;

    public WebLoadClient(WebClient webClient) {
        this.webClient = webClient;
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
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
                .block();
    }
}
