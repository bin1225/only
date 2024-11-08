package com.project.only.repository;

import com.project.only.domain.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class PageRepositoryTest {

    @Autowired
    private PageRepository pageRepository;

    @Test
    @DisplayName("페이지 저장 테스트")
    public void save(){
        //given
        String title = "testTitle";
        String content = "testContent";
        Page page = Page.builder()
                .title(title)
                .content(content)
                .build();

        //when
        Page result = pageRepository.save(page);

        //that
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("페이지 단일 조회 테스트")
    public void find(){
        //given
        String title = "testTitle";
        String content = "testContent";
        Page page = Page.builder()
                .title(title)
                .content(content)
                .build();

        //when
        Page result = pageRepository.save(page);
        Page find = pageRepository.find(result.getId());

        assertThat(find).isNotNull();
        assertThat(find.getId()).isNotNull();
        assertThat(find).isEqualTo(result);
    }

    @Test
    @DisplayName("페이지 수정 테스트")
    public void update(){
        //given
        Page page = Page.builder()
                .title("title")
                .content("content")
                .build();

        pageRepository.save(page);

        //when
        page.changeTitle("titleChanged");
        page.changeContent("contentChanged");
        Page find = pageRepository.find(page.getId());

        //then
        assertThat(find.getTitle()).isEqualTo("titleChanged");
        assertThat(find.getContent()).isEqualTo("contentChanged");
    }
}
