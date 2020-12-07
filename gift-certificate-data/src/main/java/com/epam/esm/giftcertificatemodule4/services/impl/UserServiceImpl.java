package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserDAO;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> findAll(int firstResult, int maxResults) {
        firstResult = Math.max(firstResult, 0);
        maxResults = Math.max(maxResults, 5);
        return userDAO.findAll(firstResult, maxResults);
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public Boolean existsByName(String name) {
        return userDAO.existsByName(name);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Transactional
    @Override
    public void save(User object) {
        userDAO.save(object);
    }

    @Transactional
    @Override
    public void update(User object) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public void delete(User object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }
}
