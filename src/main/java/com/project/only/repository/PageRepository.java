package com.project.only.repository;

import com.project.only.domain.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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
}
