package com.project.only.repository;

import com.project.only.domain.Diary;
import com.project.only.domain.Member;
import com.project.only.domain.MemberDiary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class DiaryRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired private MemberRepository memberRepository;
    @Test
    @DisplayName("일기장 생성 테스트")
    public void save(){
        //given
        Diary diary = diary();

        //when
        Diary result = diaryRepository.save(diary);

        //then
        Assertions.assertThat(result.getId()).isNotNull();
    }

    @Test
    @DisplayName("일기장 조회 테스트")
    public void findOne(){
        //given
        Diary diary = diary();
        diaryRepository.save(diary);

        //when
        Diary result = diaryRepository.findOne(diary.getId());

        //then
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(diary.getTitle());
        Assertions.assertThat(result.getSubTitle()).isEqualTo(diary.getSubTitle());
    }

    @Test
    @DisplayName("사용자 일기장 목록 조회")
    public void findAllByMemberId(){
        //given
        Diary diary = diary();
        Member member = Member.builder().name("kkb").build(); em.persist(member);
        MemberDiary memberDiary = new MemberDiary(member, diary);
        diary.setMember(memberDiary);
        diaryRepository.save(diary);

        //when
        List<Diary> result = diaryRepository.findAllByMemberId(member.getId());

        //then
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.getFirst().getId()).isNotNull();
    }

    private static Diary diary() {
        return Diary.builder()
                .title("abc")
                .subTitle("this is a sub title")
                .build();
    }
}
