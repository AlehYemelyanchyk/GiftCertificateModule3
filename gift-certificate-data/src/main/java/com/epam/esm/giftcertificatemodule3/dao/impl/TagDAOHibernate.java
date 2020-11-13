package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAOHibernate implements TagDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public TagDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Tag object) {
        Session session = sessionFactory.getCurrentSession();
        Tag savedTag = findByName(object.getName());
        if (savedTag == null) {
            session.save(object);
        } else {
            object.setId(savedTag.getId());
        }
    }

    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Query<Tag> query = session.createQuery("from Tag", Tag.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Tag findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        return tag;
    }

    @Override
    public Tag findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Tag where name=:name");
        query.setParameter("name", name);
        Tag tag = (Tag) query.uniqueResult();
        if (tag != null) {
            Hibernate.initialize(tag.getCertificates());
        }
        return tag;
    }

    @Override
    public void update(Tag object) {
        Session session = sessionFactory.getCurrentSession();
        session.update(object);
    }

    @Override
    public void delete(Tag object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }

    @Override
    public void deleteById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Tag where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
