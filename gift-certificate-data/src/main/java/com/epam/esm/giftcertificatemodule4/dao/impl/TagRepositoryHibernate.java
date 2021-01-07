package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.TagRepositoryCustom;
import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Order;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class TagRepositoryHibernate implements TagRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findMostPopularTags(Pageable pageable) {
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

        return query.getResultList();
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
