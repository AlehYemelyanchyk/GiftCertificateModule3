package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateRepositoryCustom;
import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateRepositoryCustomImpl implements GiftCertificateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder, Pageable pageable) {

        boolean sortDesc = searchParametersHolder.getSortOrder() != null
                && searchParametersHolder.getSortOrder().toLowerCase().equals("desc");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
        Root<Tag> tagRoot = cq.from(Tag.class);

        Join<Tag, GiftCertificate> tagGiftCertificateJoin = tagRoot.join("certificates", JoinType.INNER);

        final List<Predicate> andPredicates = new ArrayList<>();

        if (searchParametersHolder.getTagName() != null) {
            andPredicates.add(cb.equal(tagRoot.get("name"), searchParametersHolder.getTagName()));
        }
        if (searchParametersHolder.getName() != null) {
            andPredicates.add(cb.like(tagGiftCertificateJoin.get("name"),
                    "%" + searchParametersHolder.getName() + "%"));
        }
        if (searchParametersHolder.getDescription() != null) {
            andPredicates.add(cb.like(tagGiftCertificateJoin.get("description"),
                    "%" + searchParametersHolder.getDescription() + "%"));
        }
        if (searchParametersHolder.getSortBy() != null && sortDesc) {
            cq.orderBy(cb.desc(tagGiftCertificateJoin.get(searchParametersHolder.getSortBy())));
        } else if (searchParametersHolder.getSortBy() != null) {
            cq.orderBy(cb.asc(tagGiftCertificateJoin.get(searchParametersHolder.getSortBy())));
        }

        cq.select(tagGiftCertificateJoin)
                .where(andPredicates.toArray(new Predicate[andPredicates.size()]))
                .distinct(true);

        TypedQuery<GiftCertificate> query = entityManager.createQuery(cq);

        return query.getResultList();
    }
}
