package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDAOHibernate implements OrderDAO {

    private final EntityManagerFactory emf;

    @Autowired
    public OrderDAOHibernate(@Qualifier("factory") EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Order order) {
        EntityManager em = emf.createEntityManager();
        em.joinTransaction();
        order.setDate(ZonedDateTime.now().toOffsetDateTime());
        em.persist(order);
    }

    @Override
    public List<Order> findAll(int firstResult, int maxResults) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Order> query = em.createQuery("select o from Order o", Order.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Order findById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Order.class, id);
    }

    @Override
    public void update(Order object) {
        EntityManager em = emf.createEntityManager();
        em.joinTransaction();
        em.merge(object);
    }

    @Override
    public void delete(Order object) {
        EntityManager em = emf.createEntityManager();
        em.joinTransaction();
        em.remove(object);
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.joinTransaction();
        Query query = em.createQuery("delete from Order o where o.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
