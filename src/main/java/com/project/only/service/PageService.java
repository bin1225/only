package com.project.only.service;

import com.project.only.domain.Diary;
import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageUpdateDTO;
import com.project.only.error.PageErrorCode;
import com.project.only.error.RestApiException;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Page updatePage(PageUpdateDTO pageUpdateDTO) {
        Page page = pageRepository.findOne(pageUpdateDTO.getPageId());
        checkLimitTimePassed(page);

        page.changeTitle(pageUpdateDTO.getTitle());
        page.changeContent(pageUpdateDTO.getContent());

        return page;
    }

    private void checkLimitTimePassed(Page page) {
        LocalDateTime createDateTime = page.getCreateDateTime();
        if(createDateTime.isBefore(LocalDateTime.now().minusHours(24))){
            throw new RestApiException(PageErrorCode.LIMIT_TIME_PASSED);
        }
    }
}
