package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDAOHibernate implements OrderDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public OrderDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Order order) {
        order.setDate(ZonedDateTime.now().toOffsetDateTime());
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.save(order);
    }

    @Override
    public List<Order> findAll(int firstResult, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("from Order", Order.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Order findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    public void update(Order object) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.update(object);
    }

    @Override
    public void delete(Order object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Order where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
