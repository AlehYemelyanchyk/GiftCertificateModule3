package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateDAOHibernate implements GiftCertificateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        GiftCertificate merged = entityManager.merge(giftCertificate);
        giftCertificate.setId(merged.getId());
        giftCertificate.setTags(merged.getTags());
    }

    @Override
    public List<GiftCertificate> findAll(int firstResult, int maxResults) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery("select c from GiftCertificate c", GiftCertificate.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public GiftCertificate findById(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder,
                                        int firstResult, int maxResults) {

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
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.getResultList();
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        GiftCertificate persistedGiftCertificate = findById(giftCertificate.getId());
        setUpdatedFields(giftCertificate, persistedGiftCertificate);
        entityManager.merge(giftCertificate);
        persistedGiftCertificate = findById(giftCertificate.getId());
        giftCertificate.setTags(persistedGiftCertificate.getTags());
    }

    @Override
    public void delete(GiftCertificate object) {
        entityManager.remove(object);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from GiftCertificate c where c.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private void setUpdatedFields(GiftCertificate giftCertificate, GiftCertificate persistedGiftCertificate) {
        if (giftCertificate.getTags() == null) {
            giftCertificate.setTags(persistedGiftCertificate.getTags());
        }
        if (giftCertificate.getName() == null) {
            giftCertificate.setName(persistedGiftCertificate.getName());
        }
        if (giftCertificate.getDescription() == null) {
            giftCertificate.setDescription(persistedGiftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() == null) {
            giftCertificate.setPrice(persistedGiftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() == null) {
            giftCertificate.setDuration(persistedGiftCertificate.getDuration());
        }
        giftCertificate.setCreateDate(persistedGiftCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
    }
}
