package com.project.only.repository;

import com.project.only.domain.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PageRepository {

    @PersistenceContext
    private EntityManager em;

    public Page save(Page page) {
        em.persist(page);
        return page;
    }

    public Page findOne(Long id) {
        return em.find(Page.class, id);
    }

    public List<Page> findAllByDiaryId(Long diaryId) {
        return em.createQuery("select p from Page p " +
                        "where p.diary.id = :diaryId", Page.class)
                .setParameter("diaryId",diaryId)
                .getResultList();
    }
}
