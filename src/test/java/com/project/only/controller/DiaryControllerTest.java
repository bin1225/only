package com.project.only.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.only.domain.Diary;
import com.project.only.domain.DiaryRequest;
import com.project.only.domain.DiaryResponse;
import com.project.only.service.DiaryService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DiaryControllerTest {

    @InjectMocks
    private DiaryController diaryController;
    @Mock
    private DiaryService diaryService;

    private Gson gson;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(diaryController).build();
    }

    @Test
    @DisplayName("다이어리 생성 테스트")
    public void createDiary() throws Exception {
        //given
        Long memberId = 1L;
        DiaryRequest diaryRequest = new DiaryRequest("title", "subTitle");
        doReturn(diary()).when(diaryService).createDiary(any(Long.class), any(DiaryRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/only/{memberId}/diary",memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(diaryRequest))
        );

        //then
        resultActions.andExpect(status().isCreated());

        Assertions.assertThat(resultActions.andReturn().getResponse()).isNotNull();
        DiaryResponse diaryResponse = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8),DiaryResponse.class);
        Assertions.assertThat(diaryResponse.getId()).isNotNull();
    }

    @Test
    @DisplayName("다이어리 목록 조회")
    public void getDiaryList() throws Exception {
        //given
        Long memberId = 1L;
        List<Diary> diaryList = new ArrayList<>();
        diaryList.add(diary());
        doReturn(diaryList).when(diaryService).getDiaries(memberId);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/only/{memberId}/diary", memberId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());

        Type listType = new TypeToken<List<DiaryResponse>>() {}.getType();
        List<DiaryResponse> result = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(), listType);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
    }

    private Diary diary() {
        return Diary.builder()
                .id(1L)
                .title("abc")
                .subTitle("this is a sub title")
                .build();
    }
}
