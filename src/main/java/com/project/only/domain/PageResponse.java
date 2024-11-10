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
}
