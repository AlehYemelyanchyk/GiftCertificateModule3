package com.epam.esm.giftcertificatemodule4.dao.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserRepositoryCustom;
import com.epam.esm.giftcertificatemodule4.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(String id) {
        return entityManager.find(User.class, id);
    }
}
