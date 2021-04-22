package com.code.challenge.restconsumer.controller;

import com.code.challenge.restconsumer.dto.PostJson;
import com.code.challenge.restconsumer.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest {

    @Mock
    private PostService postService;

    private PostController postController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        postService = Mockito.mock(PostService.class);
        postController = new PostController(postService, new ObjectMapper(), new XmlMapper());
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void should_delegateInService_when_findAllIsCalled() throws Exception {
        // GIVEN
        String controllerPath = "/posts";

        when(postService.findAll()).thenReturn(Flux.just(PostJson.builder().build()));

        // WHEN
        mockMvc.perform(get(controllerPath)).andExpect(status().isOk());

        // THEN
        verify(postService, times(1)).findAll();
    }

    @Test
    public void should_delegateInService_when_findByIdIsCalled() throws Exception {
        // GIVEN
        String controllerPath = "/posts/1";

        when(postService.findById(1L)).thenReturn(Mono.just(PostJson.builder().build()));

        // WHEN
        mockMvc.perform(get(controllerPath)).andExpect(status().isOk());

        // THEN
        verify(postService, times(1)).findById(1L);
    }

    @Test
    public void should_delegateInService_when_createPostIsCalled() throws Exception {
        // GIVEN
        String controllerPath = "/posts";
        String body = "<post>\n" +
                "    <userId>1</userId>\n" +
                "    <title>This is a new title</title>\n" +
                "    <body>This is a new body</body>\n" +
                "</post>";

        when(postService.createPost(Mockito.any(PostJson.class))).thenReturn(Mono.just(PostJson.builder().build()));

        // WHEN
        mockMvc.perform(post(controllerPath)
                .contentType(MediaType.APPLICATION_XML)
                .content(body)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());

        // THEN
        verify(postService, times(1)).createPost(Mockito.any(PostJson.class));
    }

    @Test
    public void should_delegateInService_when_patchPostIsCalled() throws Exception {
        // GIVEN
        String controllerPath = "/posts/1";
        String body = "<post>\n" +
                "    <userId>1</userId>\n" +
                "    <title>This is a new title</title>\n" +
                "    <body>This is a new body</body>\n" +
                "</post>";

        when(postService.patchPost(Mockito.anyString(), Mockito.anyLong())).thenReturn(Mono.just(PostJson.builder().build()));

        // WHEN
        mockMvc.perform(patch(controllerPath)
                .contentType(MediaType.APPLICATION_XML)
                .content(body)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());

        // THEN
        verify(postService, times(1)).patchPost(Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    public void should_delegateInService_when_replacePostIsCalled() throws Exception {
        // GIVEN
        String controllerPath = "/posts/1";
        String body = "<post>\n" +
                "    <userId>1</userId>\n" +
                "    <title>This is a new title</title>\n" +
                "    <body>This is a new body</body>\n" +
                "</post>";

        when(postService.replacePost(Mockito.any(PostJson.class))).thenReturn(Mono.just(PostJson.builder().build()));

        // WHEN
        mockMvc.perform(put(controllerPath)
                .contentType(MediaType.APPLICATION_XML)
                .content(body)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());

        // THEN
        verify(postService, times(1)).replacePost(Mockito.any(PostJson.class));
    }

    @Test
    public void should_delegateInService_when_deletePostIsCalled() throws Exception {
        // GIVEN
        String controllerPath = "/posts/1";

        doNothing().when(postService).deletePost(1L);

        // WHEN
        mockMvc.perform(delete(controllerPath)).andExpect(status().isOk());

        // THEN
        verify(postService, times(1)).deletePost(1L);
    }

}