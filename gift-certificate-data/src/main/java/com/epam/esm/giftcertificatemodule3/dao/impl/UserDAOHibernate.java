package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.UserDAO;
import com.epam.esm.giftcertificatemodule3.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOHibernate implements UserDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDAOHibernate(@Qualifier("factory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> findAll(int firstResult, int maxResults) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(User.class, id);
    }

    @Override
    public void save(User object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long integer) {
        throw new UnsupportedOperationException();
    }
}
