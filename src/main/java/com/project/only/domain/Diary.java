package com.project.only.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;
    @Column(length = 50)
    private String title;
    @Column
    private String subTitle;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Page> pages = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberDiary> memberDiaries = new ArrayList<>();

    public Diary addPage(Page page){
        pages.add(page);
        return this;
    }

    public void setMember(MemberDiary memberDiary){
        if(this.memberDiaries.size()<2){
            this.memberDiaries.add(memberDiary);
        }
    }
}
