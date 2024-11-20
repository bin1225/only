package com.project.only.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor
public class PageResponse {
    private final Long pageId;
    private final Long diaryId;
    private final String title;
    private final String content;
    private final Date createDateTime;
    private final Date updateDateTime;

    public static PageResponse of(Page page) {
        return PageResponse.builder()
                .pageId(page.getId())
                .diaryId(page.getDiary().getId())
                .title(page.getTitle())
                .content(page.getContent())
                .createDateTime(page.getCreateDateTime())
                .updateDateTime(page.getUpdateDateTime())
                .build();
    }
}
