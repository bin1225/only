package com.project.only.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageUpdateDTO {
    private Long pageId;
    private String title;
    private String content;
}
