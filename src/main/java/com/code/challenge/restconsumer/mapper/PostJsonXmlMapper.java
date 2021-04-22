package com.code.challenge.restconsumer.mapper;

import com.code.challenge.restconsumer.dto.PostJson;
import com.code.challenge.restconsumer.dto.PostXml;


public class PostJsonXmlMapper {

    public static PostXml map2Xml(PostJson postJson) {

        return PostXml.builder()
                .id(postJson.getId())
                .userId(postJson.getUserId())
                .title(postJson.getTitle())
                .body(postJson.getBody())
                .build();
    }

    public static PostJson map2Json(PostXml postXml, Long id) {

        return PostJson.builder()
                .id(id)
                .title(postXml.getTitle())
                .body(postXml.getBody())
                .userId(postXml.getUserId())
                .build();
    }

}
