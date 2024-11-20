package com.project.only.repository;

import com.project.only.domain.Diary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiaryRepository {

    @PersistenceContext
    private EntityManager em;

    public Diary save(Diary diary) {
        em.persist(diary);
        return diary;
    }

    public Diary findOne(Long id) {
        return em.find(Diary.class, id);
    }

    public List<Diary> findAllByMemberId(Long memberId) {
        return em.createQuery("select d from Diary d " +
                        "join MemberDiary md on md.diary = d " +
                        "where md.member.id = :memberId", Diary.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
