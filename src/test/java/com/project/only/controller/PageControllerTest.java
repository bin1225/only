package com.project.only.controller;

import com.google.gson.*;
import com.project.only.domain.*;
import com.project.only.service.PageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException {
                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                    }
                }).create();
        mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();
    }

    @Test
    @DisplayName("페이지 추가 테스트")
    public void addPage() throws Exception {
        //given
        doReturn(page()).when(pageService).savePage(any(PageRequest.class));

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

    @Test
    @DisplayName("단일 페이지 조회 테스트")
    void getPage() throws Exception{
        //given
        doReturn(page()).when(pageService).findPageById(pageId);

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

    @Test
    @DisplayName("작성 후 24시간 이내인 경우 페이지 수정 성공")
    public void updatePageSuccess() throws Exception {
        //given
        doReturn(page()).when(pageService).updatePage(any(PageUpdateDTO.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/only/page/{pageId}", pageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(pageRequest()))
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    private PageRequest pageRequest(){
        return new PageRequest(diaryId, title, content);
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
