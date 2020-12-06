package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule4.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDAOHibernate implements OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Order order) {
        order.setDate(ZonedDateTime.now().toOffsetDateTime());
        entityManager.persist(order);
    }

    @Override
    public List<Order> findAll(int firstResult, int maxResults) {
        TypedQuery<Order> query = entityManager.createQuery("select o from Order o", Order.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void update(Order object) {
        entityManager.merge(object);
    }

    @Override
    public void delete(Order object) {
        entityManager.remove(object);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Order o where o.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
