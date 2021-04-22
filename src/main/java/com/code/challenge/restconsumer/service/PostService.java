package com.code.challenge.restconsumer.service;

import com.code.challenge.restconsumer.dto.PostJson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

    private final WebClient webClient;

    public PostService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://jsonplaceholder.typicode.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Flux<PostJson> findAll()
    {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(PostJson.class);
    }

    public Mono<PostJson> findById(Long id)
    {
        return webClient.get()
                .uri("/posts/" + id)
                .exchangeToMono(response -> {
                    if (response.statusCode()
                            .equals(HttpStatus.OK)) {
                        return response.bodyToMono(PostJson.class);
                    } else {
                        throw new IllegalArgumentException();
                    }
                });
    }
    public Mono<PostJson> patchPost(String postJson, Long id)
    {
        return webClient.patch()
                .uri("/posts/" + id)
                .body(Mono.just(postJson), String.class)
                .exchangeToMono(response -> {
                    if (response.statusCode()
                            .equals(HttpStatus.OK)) {
                        return response.bodyToMono(PostJson.class);
                    } else {
                        throw new IllegalArgumentException();
                    }
                });
    }

    public Mono<PostJson> createPost(PostJson post)
    {
        return webClient.post()
                .uri("/posts")
                .body(Mono.just(post), PostJson.class)
                .retrieve()
                .bodyToMono(PostJson.class);
    }

    public Mono<PostJson> replacePost(PostJson postJson)
    {
        return webClient.put()
                .uri("/posts/" + postJson.getId())
                .body(Mono.just(postJson), PostJson.class)
                .exchangeToMono(response -> {
                    if (response.statusCode()
                            .equals(HttpStatus.OK)) {
                        return response.bodyToMono(PostJson.class);
                    } else {
                        throw new IllegalArgumentException();
                    }
                });
    }

    public void deletePost(Long id)
    {
        webClient.delete()
            .uri("/posts/" + id)
            .retrieve()
            .bodyToMono(Void.class);
    }

}
