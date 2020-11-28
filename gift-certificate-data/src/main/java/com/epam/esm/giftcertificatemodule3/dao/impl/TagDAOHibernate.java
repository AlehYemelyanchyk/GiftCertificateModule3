package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class TagDAOHibernate implements TagDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public TagDAOHibernate(@Qualifier("factory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Tag object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        em.persist(object);
    }

    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Tag> query = em.createQuery("select t from Tag t", Tag.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Tag findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Tag> query = em.createQuery("select t from Tag t where t.name = :name", Tag.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Tag> findMostPopularTags(int firstResult, int maxResults) {
        Long bestCustomerId = findBestCustomerId();

        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Order> orderRoot = cq.from(Order.class);

        Join<Order, GiftCertificate> certificates = orderRoot.join("certificates", JoinType.INNER);
        Join<GiftCertificate, Tag> tags = certificates.join("tags", JoinType.INNER);

        cq.select(tags)
                .where(cb.equal(orderRoot.get("user"), bestCustomerId))
                .groupBy(tags)
                .orderBy(cb.desc(cb.count(tags)));

        TypedQuery<Tag> query = em.createQuery(cq);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }

    @Override
    public void update(Tag object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        em.merge(object);
    }

    @Override
    public void delete(Tag object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        em.remove(object);
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        Query query = em.createQuery("delete from Tag t where t.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private Long findBestCustomerId() {
        long zeroId = 0L;
        EntityManager em = entityManagerFactory.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<User> user = cq.from(User.class);
        Join<User, Order> order = user.join("orders", JoinType.INNER);
        cq.select(user.get("id"));
        cq.groupBy(user.get("id"));

        cq.orderBy(cb.desc(cb.sum(order.get("price"))));
        TypedQuery<Long> query = em.createQuery(cq);

        List<Long> resultList = query.getResultList();
        return resultList.stream().findFirst().orElse(zeroId);
    }
}
