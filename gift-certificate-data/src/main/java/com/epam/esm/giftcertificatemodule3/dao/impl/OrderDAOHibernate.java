package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class OrderDAOHibernate implements OrderDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public OrderDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Order object) {
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
    public List<Order> findBy(SearchParametersHolder searchParametersHolder) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);

        if (searchParametersHolder.getId() != null) {
            cr.select(root).where(cb.equal(root.get("user"), searchParametersHolder.getId()));
        }
        if (searchParametersHolder.getHighestPrice() != null && searchParametersHolder.getHighestPrice()) {
            Path<Double> pathToPrice = root.get("price");
            Subquery<Double> subQuery = cr.subquery(Double.class);
            Root<Order> subRoot = subQuery.from(Order.class);
            Path<Double> subPathToPrice = subRoot.get("price");
            subQuery.select(cb.max(subPathToPrice));
            cr.select(root).where(cb.equal(pathToPrice, subQuery));
        }

        Query<Order> query = session.createQuery(cr);
        List<Order> orders = query.getResultList();
        orders.forEach(Hibernate::initialize);
        return orders;
    }

    @Override
    public List<Order> findHighestSpendByUser(SearchParametersHolder searchParametersHolder) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cr = cb.createQuery(Order.class);
        Root<Order> root = cr.from(Order.class);

        if (searchParametersHolder.getHighestPrice() != null && searchParametersHolder.getHighestPrice()) {
            Path<Double> pathToPrice = root.get("price");
            Subquery<Double> subQuery = cr.subquery(Double.class);
            Root<Order> subRoot = subQuery.from(Order.class);
            Path<Double> subPathToPrice = subRoot.get("price");
            subQuery.select(cb.max(subPathToPrice));
            cr.select(root).where(cb.equal(pathToPrice, subQuery));
        }
        Query<Order> query = session.createQuery(cr);
        List<Order> orders = query.getResultList();
        orders.forEach(Hibernate::initialize);
        return orders;
    }

    @Override
    public void update(Order object) {
        Session session = sessionFactory.getCurrentSession();
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
