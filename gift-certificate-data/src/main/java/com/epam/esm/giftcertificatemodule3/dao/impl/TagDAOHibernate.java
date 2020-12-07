package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class TagDAOHibernate implements TagDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Tag object) {
        entityManager.persist(object);
    }

    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        TypedQuery<Tag> query = entityManager.createQuery("select t from Tag t", Tag.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery("select t from Tag t where t.name = :name", Tag.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Tag> findMostPopularTags(int firstResult, int maxResults) {
        Long bestCustomerId = findBestCustomerId();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Order> orderRoot = cq.from(Order.class);

        Join<Order, GiftCertificate> certificates = orderRoot.join("certificates", JoinType.INNER);
        Join<GiftCertificate, Tag> tags = certificates.join("tags", JoinType.INNER);

        cq.select(tags)
                .where(cb.equal(orderRoot.get("user"), bestCustomerId))
                .groupBy(tags)
                .orderBy(cb.desc(cb.count(tags)));

        TypedQuery<Tag> query = entityManager.createQuery(cq);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }

    @Override
    public void update(Tag object) {
        entityManager.merge(object);
    }

    @Override
    public void delete(Tag object) {
        entityManager.remove(object);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Tag t where t.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private Long findBestCustomerId() {
        long zeroId = 0L;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<User> user = cq.from(User.class);
        Join<User, Order> order = user.join("orders", JoinType.INNER);
        cq.select(user.get("id"));
        cq.groupBy(user.get("id"));

        cq.orderBy(cb.desc(cb.sum(order.get("price"))));
        TypedQuery<Long> query = entityManager.createQuery(cq);

        List<Long> resultList = query.getResultList();
        return resultList.stream().findFirst().orElse(zeroId);
    }
}
