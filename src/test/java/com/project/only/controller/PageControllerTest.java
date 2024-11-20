package com.project.only.controller;

import com.google.gson.Gson;
import com.project.only.domain.PageRequest;
import com.project.only.domain.PageResponse;
import com.project.only.service.PageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PageControllerTest {

    @InjectMocks
    private PageController pageController;
    @Mock
    private PageService pageService;

    private Gson gson;
    private MockMvc mockMvc;

    private final Long pageId = 100L;
    private final Long diaryId = 1L;
    private final String title = "title";
    private final String content = "content";

    @BeforeEach
    void setUp() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();
    }

    @Test
    public void addPage() throws Exception {
        //given
        doReturn(pageResponse()).when(pageService).savePage(any(PageRequest.class));

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/only/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(pageRequest()))
        );
        //then
        resultActions.andExpect(status().isCreated());

        Assertions.assertThat(resultActions.andReturn().getResponse()).isNotNull();
        final PageResponse response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8),PageResponse.class);

        Assertions.assertThat(response.getPageId()).isNotNull();
    }

    @Test void getPage() throws Exception{
        //given
        doReturn(pageResponse()).when(pageService).findPageById(pageId);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/only/page/{pageId}",pageId));

        //then
        resultActions.andExpect(status().isOk());

        PageResponse response = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(), PageResponse.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getTitle()).isEqualTo(title);
        Assertions.assertThat(response.getContent()).isEqualTo(content);
    }

    private PageRequest pageRequest(){
        return new PageRequest(diaryId, title, content);
    }

    private PageResponse pageResponse(){
        return PageResponse.builder()
                .pageId(pageId)
                .title(title)
                .content(content)
                .build();
    }
}
