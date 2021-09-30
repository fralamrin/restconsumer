package com.code.challenge.restconsumer.mapper;

import com.code.challenge.restconsumer.dto.PostJson;
import com.code.challenge.restconsumer.dto.PostXml;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostJsonXmlMapperTest {

    private PostJson postJson;
    private PostXml postXml;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        Long userId = 2L;
        String title = "A title";
        String body = "A body";

        postJson = PostJson.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .body(body)
                .build();

        postXml = PostXml.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .body(body)
                .build();
    }

    @Test
    void map2Xml() {
       PostXml postXmlNew = PostJsonXmlMapper.map2Xml(postJson);

       assertThat(postXmlNew).isEqualTo(postXml);
    }

    @Test
    void map2Json() {
        PostJson postJsonNew = PostJsonXmlMapper.map2Json(postXml, postXml.getId());

        assertThat(postJsonNew).isEqualTo(postJson);
    }
}