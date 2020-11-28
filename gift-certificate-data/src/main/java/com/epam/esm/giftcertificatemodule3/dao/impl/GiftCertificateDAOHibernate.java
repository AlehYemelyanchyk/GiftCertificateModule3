package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateDAOHibernate implements GiftCertificateDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public GiftCertificateDAOHibernate(@Qualifier("factory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        GiftCertificate merged = em.merge(giftCertificate);
        giftCertificate.setId(merged.getId());
        giftCertificate.setTags(merged.getTags());
    }

    @Override
    public List<GiftCertificate> findAll(int firstResult, int maxResults) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<GiftCertificate> query = em.createQuery("select c from GiftCertificate c", GiftCertificate.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public GiftCertificate findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(GiftCertificate.class, id);
    }

    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder,
                                        int firstResult, int maxResults) {

        boolean sortDesc = searchParametersHolder.getSortOrder() != null
                && searchParametersHolder.getSortOrder().toLowerCase().equals("desc");

        EntityManager em = entityManagerFactory.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
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

        TypedQuery<GiftCertificate> query = em.createQuery(cq);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        List<GiftCertificate> certificates = query.getResultList();

        return certificates;
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        GiftCertificate persistedGiftCertificate = findById(giftCertificate.getId());
        setUpdatedFields(giftCertificate, persistedGiftCertificate);
        em.merge(giftCertificate);
        persistedGiftCertificate = findById(giftCertificate.getId());
        giftCertificate.setTags(persistedGiftCertificate.getTags());
    }

    @Override
    public void delete(GiftCertificate object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        em.remove(object);
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.joinTransaction();
        Query query = em.createQuery("delete from GiftCertificate c where c.id=:id");
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
