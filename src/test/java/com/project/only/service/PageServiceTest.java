package com.project.only.service;


import com.project.only.domain.Diary;
import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.PageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class PageServiceTest {

    @InjectMocks
    private PageService pageService;
    @Mock
    private PageRepository pageRepository;
    @Mock
    private DiaryRepository diaryRepository;

    private final Long pageId = 100L;
    private final Long diaryId = 1L;
    private final String title = "title";
    private final String content = "content";

    @Test
    @DisplayName("페이지 저장 테스트")
    public void savePage(){
        //given
        doReturn(page()).when(pageRepository).save(any(Page.class));
        doReturn(diary()).when(diaryRepository).findOne(diaryId);

        //when
        PageResponse result = pageService.savePage(new PageRequest(diaryId, title, content));

        //then
        Assertions.assertThat(result.getPageId()).isNotNull();
        Assertions.assertThat(result.getDiaryId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("아이디로 페이지 조회 테스트")
    public void findPageById(){
        //given
        doReturn(page()).when(pageRepository).findOne(pageId);

        //when
        PageResponse result = pageService.findPageById(pageId);

        //then
        Assertions.assertThat(result.getPageId()).isNotNull();
        Assertions.assertThat(result.getDiaryId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
    }

    private Page page(){
        return Page.builder()
                .id(pageId)
                .title(title)
                .content(content)
                .diary(diary())
                .build();
    }

    private Diary diary() {
        return Diary.builder()
                .id(diaryId)
                .title("abc")
                .subTitle("this is a sub title")
                .build();
    }

}
