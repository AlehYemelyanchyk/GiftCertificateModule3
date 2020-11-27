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

    private final EntityManagerFactory emf;

    @Autowired
    public UserDAOHibernate(@Qualifier("factory") EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<User> findAll(int firstResult, int maxResults) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        List<User> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public User findById(Long id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        return user;
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
