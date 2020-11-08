package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TagDAOHibernate implements TagDAO {

    private EntityManager entityManager;

    @Autowired
    public TagDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        Session session = entityManager.unwrap(Session.class);
        Query<Tag> query = session.createQuery("from Tag", Tag.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Tag findById(Integer id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Tag where name=:name");
        query.setParameter("name", name);
        return (Tag) query.uniqueResult();
    }

    @Override
    public void save(Tag object) {
        Session session = entityManager.unwrap(Session.class);
        session.save(object);
    }

    @Override
    public void update(Tag object) {
        Session session = entityManager.unwrap(Session.class);
        session.update(object);
    }

    @Override
    public void delete(Tag object) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from Tag where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
