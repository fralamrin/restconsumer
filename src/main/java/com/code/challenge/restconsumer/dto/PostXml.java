package com.code.challenge.restconsumer.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "post")
public class PostXml {
    private Long id;
    private Long userId;
    private String title;
    private String body;
}
