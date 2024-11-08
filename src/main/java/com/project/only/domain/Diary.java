package com.project.only.domain;

import jakarta.persistence.*;

import java.util.Date;

public class Diary {
    @Id @GeneratedValue
    private int id;
    @Column(length = 50)
    private String title;
    @Column
    private String subTitle;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;
}
