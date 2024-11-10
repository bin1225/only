package com.project.only.service;

import com.project.only.domain.Diary;
import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final DiaryRepository diaryRepository;

    public PageResponse savePage(PageRequest pageRequest) {
        Diary diary = diaryRepository.findOne(pageRequest.getDiaryId());
        Page page = Page.builder()
                .title(pageRequest.getTitle())
                .content(pageRequest.getContent())
                .diary(diary)
                .build();
        Page save = pageRepository.save(page);

        return PageResponse.builder()
                .pageId(save.getId())
                .diaryId(save.getDiary().getId())
                .title(save.getTitle())
                .content(save.getContent())
                .createDateTime(save.getCreateDateTime())
                .updateDateTime(save.getUpdateDateTime())
                .build();
    }


    public PageResponse findPageById(Long id) {
        Page save = pageRepository.findOne(id);

        return PageResponse.builder()
                .pageId(save.getId())
                .diaryId(save.getDiary().getId())
                .title(save.getTitle())
                .content(save.getContent())
                .createDateTime(save.getCreateDateTime())
                .updateDateTime(save.getUpdateDateTime())
                .build();
    }
}
