package com.project.only.service;


import com.project.only.domain.Page;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
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

    private final Long id = 1L;
    private final String title = "title";
    private final String content = "content";

    @Test
    @DisplayName("페이지 저장 테스트")
    public void savePage(){
        //given
        doReturn(page()).when(pageRepository).save(any(Page.class));

        //when
        PageResponse result = pageService.savePage(new PageRequest(title, content));

        //then
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("아이디로 페이지 조회 테스트")
    public void findPageById(){
        //given
        doReturn(page()).when(pageRepository).findOne(id);

        //when
        PageResponse result = pageService.findPageById(id);

        //then
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
    }

    private Page page(){
        return Page.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
