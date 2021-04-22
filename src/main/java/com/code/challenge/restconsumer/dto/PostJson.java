package com.code.challenge.restconsumer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostJson {

    private Long userId;
    private Long id;
    private String title;
    private String body;
}
