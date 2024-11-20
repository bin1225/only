package com.project.only.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class MemberDiaryId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "diary_id")
    private Long diaryId;

    public MemberDiaryId(Long memberId, Long diaryId) {
        this.memberId = memberId;
        this.diaryId = diaryId;
    }
}
