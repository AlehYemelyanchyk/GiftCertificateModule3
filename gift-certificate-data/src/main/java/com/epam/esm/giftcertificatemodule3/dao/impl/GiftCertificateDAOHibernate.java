package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class GiftCertificateDAOHibernate implements GiftCertificateDAO {

    private EntityManager entityManager;

    @Autowired
    public GiftCertificateDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll(int firstResult, int maxResults) {
        Session session = entityManager.unwrap(Session.class);
        Query<GiftCertificate> query = session.createQuery("from GiftCertificate", GiftCertificate.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public GiftCertificate findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(GiftCertificate.class, id);
    }

    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder) {
        Session session = entityManager.unwrap(Session.class);
        Query<GiftCertificate> query = searchByRequestBuilder(session, searchParametersHolder);
        return query.getResultList();
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        Session session = entityManager.unwrap(Session.class);
        session.clear();
        session.save(giftCertificate);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        Session session = entityManager.unwrap(Session.class);
        GiftCertificate oldGiftCertificate = findById(giftCertificate.getId());
        setUpdatedFields(giftCertificate, oldGiftCertificate);
        session.clear();
        session.saveOrUpdate(giftCertificate);
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(giftCertificate);
    }

    @Override
    public void deleteById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from GiftCertificate where id=:id_cert");
        query.setParameter("id_cert", id);
        query.executeUpdate();
    }

    private Query<GiftCertificate> searchByRequestBuilder(Session session, SearchParametersHolder searchParametersHolder) {
        String requestBegin = "from GiftCertificate where ";
        Long id = searchParametersHolder.getId();
        String tagName = searchParametersHolder.getTagName();
        String name = searchParametersHolder.getName();
        String description = searchParametersHolder.getDescription();
        String sortBy = searchParametersHolder.getSortBy();
        String sortOrder = searchParametersHolder.getSortOrder();

        StringBuilder queryString = new StringBuilder();
        queryString.append(requestBegin);
        queryString.append((id == null) ? "" : " id=:id_cert");
        queryString.append((tagName == null) ? "" : " tagName like concat('%',:tagName,'%')");
        queryString.append((name == null) ? "" : " name like concat('%',:name,'%')");
        queryString.append((description == null) ? "" : " description like concat('%',:description,'%')");
        queryString.append((sortBy == null) ? "" : " ORDER BY " + sortBy);
        queryString.append((sortBy == null || sortOrder == null) ? "" : " " + sortOrder);

        Query<GiftCertificate> query = session.createQuery(queryString.toString(), GiftCertificate.class);
        if (id != null) {
            query.setParameter("id_cert", id);
        }
        if (name != null) {
            query.setParameter("name", name);
        }
        if (tagName != null) {
            query.setParameter("tagName", tagName);
        }
        if (description != null) {
            query.setParameter("description", description);
        }
        return query;
    }

    private void setUpdatedFields(GiftCertificate giftCertificate, GiftCertificate oldGiftCertificate) {
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
    }
}
