package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.entity.User;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class TagDAOHibernate implements TagDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    @Autowired
    public TagDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Tag object) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Tag savedTag = findByName(object.getName());
        if (savedTag == null) {
            session.save(object);
        } else {
            object.setId(savedTag.getId());
        }
    }

    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Query<Tag> query = session.createQuery("from Tag", Tag.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Tag findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Tag where name=:name");
        query.setParameter("name", name);
        Tag tag = (Tag) query.uniqueResult();
        if (tag != null) {
            Hibernate.initialize(tag.getCertificates());
        }
        return tag;
    }

    @Override
    public List<Tag> findMostPopularTags(SearchParametersHolder searchParametersHolder, int firstResult, int maxResults) {
        Long bestCustomerId = findBestCustomerId();

        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Order> orderRoot = cq.from(Order.class);

        Join<Order, GiftCertificate> certificates = orderRoot.join("certificates", JoinType.INNER);
        Join<GiftCertificate, Tag> tags = certificates.join("tags", JoinType.INNER);

        cq.select(tags)
                .where(cb.equal(orderRoot.get("user"), bestCustomerId))
                .groupBy(tags)
                .orderBy(cb.desc(cb.count(tags)));

        Query<Tag> query = session.createQuery(cq);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }

    @Override
    public void update(Tag object) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.update(object);
    }

    @Override
    public void delete(Tag object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Tag where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private Long findBestCustomerId() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<User> user = cq.from(User.class);
        Join<User, Order> order = user.join("orders", JoinType.INNER);
        cq.select(user.get("id"));
        cq.groupBy(user.get("id"));

        cq.orderBy(cb.desc(cb.sum(order.get("price"))));
        Query<Long> query = session.createQuery(cq);

        List<Long> resultList = query.getResultList();
        return resultList.stream().findFirst().orElse(0L);
    }
}
