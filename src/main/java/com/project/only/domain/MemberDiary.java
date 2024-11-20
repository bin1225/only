package com.project.only.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberDiary {

    @EmbeddedId
    @Column(name = "member_diary_id")
    private MemberDiaryId id;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("diaryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public MemberDiary(Member member, Diary diary) {
        this.member = member;
        this.diary = diary;
        this.id = new MemberDiaryId(member.getId(), diary.getId());
    }
}
