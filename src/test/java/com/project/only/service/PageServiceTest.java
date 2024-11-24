package com.project.only.service;


import com.project.only.domain.*;
import com.project.only.error.PageErrorCode;
import com.project.only.error.RestApiException;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.PageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
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
        Page result = pageService.savePage(new PageRequest(diaryId, title, content));

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDiary().getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("아이디로 페이지 조회 테스트")
    public void findPageById(){
        //given
        doReturn(page()).when(pageRepository).findOne(pageId);

        //when
        Page result = pageService.findPageById(pageId);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDiary().getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("작성 후 24시간 이내인 경우 페이지 수정 성공")
    public void updatePageSuccess() throws Exception {
        //given
        Page page = Page.builder()
                .id(pageId)
                .createDateTime(LocalDateTime.now().minusHours(10))
                .build();
        doReturn(page).when(pageRepository).findOne(pageId);

        PageUpdateDTO pageUpdateDTO = PageUpdateDTO.builder()
                .pageId(page.getId())
                .title("updated title")
                .content("updated content")
                .build();


        //when
        Page result = pageService.updatePage(pageUpdateDTO);

        //then
        assertThat(result.getId()).isNotNull();
    }


    @Test
    @DisplayName("작성 후 24시간 후인 경우 페이지 수정 실패")
    public void updatePageFail() throws Exception {
        //given
        Page page = Page.builder()
                .id(pageId)
                .createDateTime(LocalDateTime.now().minusHours(25))
                .build();
        doReturn(page).when(pageRepository).findOne(pageId);

        PageUpdateDTO pageUpdateDTO = PageUpdateDTO.builder()
                .pageId(page.getId())
                .title("updated title")
                .content("updated content")
                .build();


        //when
        Throwable thrown = Assertions.catchThrowable(() -> pageService.updatePage(pageUpdateDTO));

        //then
        assertThat(thrown).isInstanceOf(RestApiException.class);

        RestApiException restApiException = (RestApiException) thrown;
        Assertions.assertThat(restApiException.getErrorCode()).isEqualTo(PageErrorCode.LIMIT_TIME_PASSED);
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
