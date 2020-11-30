package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.UserDAO;
import com.epam.esm.giftcertificatemodule3.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOHibernate implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(int firstResult, int maxResults) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
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
