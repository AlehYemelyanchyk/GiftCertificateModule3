package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserDAO;
import com.epam.esm.giftcertificatemodule4.entity.User;
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
    public User findByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select t from User t where t.name = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Boolean existsByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select t from User t where t.name = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult() != null;
    }

    @Override
    public Boolean existsByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select t from User t where t.email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult() != null;
    }

    @Override
    public void save(User object) {
        entityManager.persist(object);
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
