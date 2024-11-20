package com.project.only.service;

import com.project.only.domain.*;
import com.project.only.repository.DiaryRepository;
import com.project.only.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    public Diary createDiary(Long memberId, DiaryRequest diaryRequest) {
        Diary diary = Diary.builder()
                .title(diaryRequest.getTitle())
                .subTitle(diaryRequest.getSubTitle())
                .build();

        Member member = memberRepository.findById(memberId);
        MemberDiary memberDiary = new MemberDiary(member, diary);
        diary.setMember(memberDiary);

        return diaryRepository.save(diary);
    }

    public List<Diary> getDiaries(Long memberId) {
        return diaryRepository.findAllByMemberId(memberId);
    }
}
