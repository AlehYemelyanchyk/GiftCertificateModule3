package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.RoleService;
import com.epam.esm.giftcertificatemodule4.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAOHibernate implements RoleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll(int firstResult, int maxResults) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r", Role.class);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.name = :name", Role.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void save(Role object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Role object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Role object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long integer) {
        throw new UnsupportedOperationException();
    }
}
