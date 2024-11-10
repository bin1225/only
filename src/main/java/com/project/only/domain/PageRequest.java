package com.project.only.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PageRequest {
    private final Long diaryId;
    private final String title;
    private final String content;
}
