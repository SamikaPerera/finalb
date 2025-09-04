package com.samika.quiz_api.integration.opentdb;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@Service
public class OpenTdbClient {
    private final RestTemplate rest;
    public OpenTdbClient(RestTemplateBuilder builder) { this.rest = builder.build(); }

    public List<OpenTdbModels.Question> fetch(int amount, String category, String difficulty) {
        var uri = UriComponentsBuilder.fromHttpUrl("https://opentdb.com/api.php")
                .queryParam("amount", amount)
                .queryParam("type", "multiple");
        if (category != null && !category.isBlank()) uri.queryParam("category", category);
        if (difficulty != null && !difficulty.isBlank()) uri.queryParam("difficulty", difficulty.toLowerCase());
        var resp = rest.getForObject(uri.toUriString(), OpenTdbModels.Response.class);
        return (resp != null && resp.results != null) ? resp.results : List.of();
    }
}
