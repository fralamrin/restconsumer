package com.code.challenge.restconsumer.controller;

import com.code.challenge.restconsumer.dto.PostJson;
import com.code.challenge.restconsumer.dto.PostXml;
import com.code.challenge.restconsumer.dto.PostXmlResponse;
import com.code.challenge.restconsumer.mapper.PostJsonXmlMapper;
import com.code.challenge.restconsumer.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class PostController {

    private final PostService postService;

    private final ObjectMapper objectMapper;

    private final XmlMapper xmlMapper;

    public PostController(PostService postService, ObjectMapper objectMapper, XmlMapper xmlMapper) {
        this.postService = postService;
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    @GetMapping("/posts")
    public Mono<String> findAll() throws JsonProcessingException {
        Flux<PostJson> all = postService.findAll();
        List<PostXml> postXmlList = all.toStream().map(PostJsonXmlMapper::map2Xml).collect(Collectors.toList());
        PostXmlResponse response = PostXmlResponse.builder().posts(postXmlList).build();
        return Mono.just(xmlMapper.writeValueAsString(response));
    }

    @GetMapping("/posts/{id}")
    public Mono<String> findById(@PathVariable Long id) throws JsonProcessingException {
        Mono<PostJson> post = postService.findById(id);
        return createSinglePostResponse(post.block());
    }

    @PostMapping("/posts")
    Mono<String> createPost(@RequestBody PostXml postXml) throws JsonProcessingException {
        Mono<PostJson> post = postService.createPost(PostJsonXmlMapper.map2Json(postXml, null));
        return createSinglePostResponse(post.block());
    }

    @PatchMapping("/posts/{id}")
    Mono<String> patchPost(@RequestBody PostXml postXml, @PathVariable Long id) throws JsonProcessingException {
        PostJson postJson = PostJsonXmlMapper.map2Json(postXml, id);
        String postJsonAsString = objectMapper.writeValueAsString(postJson);
        Mono<PostJson> post = postService.patchPost(postJsonAsString, id);
        return createSinglePostResponse(post.block());
    }

    @PutMapping("/posts/{id}")
    Mono<String> replacePost(@RequestBody PostXml newPostXml, @PathVariable Long id) throws JsonProcessingException {
        Mono<PostJson> post = postService.replacePost(PostJsonXmlMapper.map2Json(newPostXml, id));
        return createSinglePostResponse(post.block());
    }

    @DeleteMapping("/posts/{id}")
    void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    private Mono<String> createSinglePostResponse(PostJson postJson) throws JsonProcessingException {
        PostXml response = PostJsonXmlMapper.map2Xml(postJson);
        return Mono.just(xmlMapper.writeValueAsString(response));
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Illegal arguments")
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentHandler() {
        //
    }

}
