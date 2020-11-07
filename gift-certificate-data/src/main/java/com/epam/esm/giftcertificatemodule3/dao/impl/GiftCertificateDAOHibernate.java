package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GiftCertificateDAOHibernate implements GiftCertificateDAO {

    private EntityManager entityManager;

    @Autowired
    public GiftCertificateDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<GiftCertificate> query = session.createQuery("from GiftCertificate", GiftCertificate.class);
        return query.getResultList();
    }

    @Override
    public GiftCertificate findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(GiftCertificate.class, id);
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
        Session session = entityManager.unwrap(Session.class);
        GiftCertificate oldGiftCertificate = findById(giftCertificate.getId());
        setUpdatedFields(giftCertificate, oldGiftCertificate);
        session.saveOrUpdate(oldGiftCertificate);
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

    private void setUpdatedFields(GiftCertificate giftCertificate, GiftCertificate oldGiftCertificate) {
        if (giftCertificate.getName() != null) {
            oldGiftCertificate.setName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            oldGiftCertificate.setDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            oldGiftCertificate.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null) {
            oldGiftCertificate.setDuration(giftCertificate.getDuration());
        }
        if (giftCertificate.getTags() != null) {
            Set<Tag> tagsSet = new HashSet<>();
            tagsSet.addAll(oldGiftCertificate.getTags());
            tagsSet.addAll(giftCertificate.getTags());
            List<Tag> tagsList = new ArrayList<>(tagsSet);
            oldGiftCertificate.setTags(tagsList);
        }
        oldGiftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
    }
}
