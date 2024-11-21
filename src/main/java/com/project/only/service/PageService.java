package com.project.only.service;

import com.project.only.domain.Diary;
import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final DiaryRepository diaryRepository;

    public Page savePage(PageRequest pageRequest) {
        Diary diary = diaryRepository.findOne(pageRequest.getDiaryId());
        Page page = Page.builder()
                .title(pageRequest.getTitle())
                .content(pageRequest.getContent())
                .diary(diary)
                .build();

        return pageRepository.save(page);
    }


    public Page findPageById(Long id) {
        return pageRepository.findOne(id);

    }

    public List<Page> findPagesOfDiary(Long diaryId) {
        return pageRepository.findAllByDiaryId(diaryId);
    }
}
