package com.project.only.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Page {

    @Id @GeneratedValue
    @Column(name = "page_id")
    private Long id;
    @Column(length = 50)
    private String title;
    @Lob
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
