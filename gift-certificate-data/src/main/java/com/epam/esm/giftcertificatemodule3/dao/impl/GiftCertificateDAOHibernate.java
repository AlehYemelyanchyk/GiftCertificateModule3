package com.epam.esm.giftcertificatemodule3.dao.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
    public void save(GiftCertificate object) {
        Session session = entityManager.unwrap(Session.class);
        session.save(object);
    }

    @Override
    public void update(GiftCertificate object) {
        Session session = entityManager.unwrap(Session.class);
        session.update(object);
    }

    @Override
    public void delete(GiftCertificate object) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from GiftCertificate where id=:id_cert");
        query.setParameter("id_cert", id);
        query.executeUpdate();
    }
}
