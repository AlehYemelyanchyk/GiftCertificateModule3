package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.UserDAO;
import com.epam.esm.giftcertificatemodule3.entity.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOHibernate implements UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> findAll(int firstResult, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User", User.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        List<User> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            resultList.forEach(user -> Hibernate.initialize(user.getOrders()));
        }
        return resultList;
    }

    @Override
    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            Hibernate.initialize(user.getOrders());
        }
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
