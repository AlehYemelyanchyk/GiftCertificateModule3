package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateDAOHibernate implements GiftCertificateDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    private SessionFactory sessionFactory;

    @Autowired
    public GiftCertificateDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.save(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findAll(int firstResult, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Query<GiftCertificate> query = session.createQuery("from GiftCertificate", GiftCertificate.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        List<GiftCertificate> certificates = query.getResultList();
        certificates.forEach(e -> Hibernate.initialize(e.getTags()));
        return certificates;
    }

    @Override
    public GiftCertificate findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        GiftCertificate giftCertificate = session.get(GiftCertificate.class, id);
        if (giftCertificate != null) {
            Hibernate.initialize(giftCertificate.getTags());
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder,
                                        int firstResult, int maxResults) {

        boolean sortDesc = searchParametersHolder.getSortOrder() != null
                && searchParametersHolder.getSortOrder().toLowerCase().equals("desc");

        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
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

        Query<GiftCertificate> query = session.createQuery(cq);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        List<GiftCertificate> certificates = query.getResultList();
        certificates.forEach(e -> Hibernate.initialize(e.getTags()));

        return certificates;
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        Session session = sessionFactory.getCurrentSession();
        GiftCertificate oldGiftCertificate = findById(giftCertificate.getId());
        setUpdatedFields(giftCertificate, oldGiftCertificate);
        session.clear();
        session.saveOrUpdate(giftCertificate);
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(giftCertificate);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from GiftCertificate where id=:id_cert");
        query.setParameter("id_cert", id);
        query.executeUpdate();
    }

    private void setUpdatedFields(GiftCertificate giftCertificate, GiftCertificate oldGiftCertificate) {
        if (giftCertificate.getTags() == null) {
            giftCertificate.setTags(oldGiftCertificate.getTags());
        }
        if (giftCertificate.getName() == null) {
            giftCertificate.setName(oldGiftCertificate.getName());
        }
        if (giftCertificate.getDescription() == null) {
            giftCertificate.setDescription(oldGiftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() == null) {
            giftCertificate.setPrice(oldGiftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() == null) {
            giftCertificate.setDuration(oldGiftCertificate.getDuration());
        }
        giftCertificate.setCreateDate(oldGiftCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
    }
}
