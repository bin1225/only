package com.project.only.service;

import com.project.only.domain.Diary;
import com.project.only.domain.DiaryRequest;
import com.project.only.domain.Member;
import com.project.only.domain.MemberDiary;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class DiaryServiceTest {

    @InjectMocks
    private DiaryService diaryService;
    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("일기장 생성 테스트")
    public void createDiary() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "kkb");
        Diary diary = diary();
        DiaryRequest diaryRequest = new DiaryRequest("title", "content");
        MemberDiary memberDiary = new MemberDiary(member, diary);
        doReturn(member).when(memberRepository).findById(memberId);
        doReturn(diary).when(diaryRepository).save(any(Diary.class));
        diary.setMember(memberDiary);

        //when
        Diary createdDiary = diaryService.createDiary(member.getId(), diaryRequest);

        //then
        Assertions.assertThat(createdDiary.getId()).isNotNull();
        Assertions.assertThat(createdDiary.getMemberDiaries().getFirst().getId()).isNotNull();
    }

    private Diary diary() {
        return Diary.builder()
                .id(1L)
                .title("abc")
                .subTitle("this is a sub title")
                .build();
    }


}
