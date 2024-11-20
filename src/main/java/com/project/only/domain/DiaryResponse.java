package com.project.only.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor
public class DiaryResponse {
    private final Long id;
    private final String title;
    private final String subTitle;
    private final Date createDateTime;
    private final Date updateDateTime;

    public static DiaryResponse of(Diary diary) {
        return DiaryResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .subTitle(diary.getSubTitle())
                .createDateTime(diary.getCreateDateTime())
                .updateDateTime(diary.getUpdateDateTime())
                .build();
    }
}
