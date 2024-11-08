package com.project.only.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    @Id @GeneratedValue
    private Long id;
    @Column(length = 50)
    private String title;
    @Lob
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
