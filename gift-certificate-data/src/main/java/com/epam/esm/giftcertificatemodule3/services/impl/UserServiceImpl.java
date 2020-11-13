package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.UserDAO;
import com.epam.esm.giftcertificatemodule3.entity.User;
import com.epam.esm.giftcertificatemodule3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public List<User> findAll(int firstResult, int maxResults) {
        return userDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public void save(User object) {
    }

    @Override
    public void update(User object) {
    }

    @Override
    public void delete(User object) {
    }

    @Override
    public void deleteById(Long aLong) {
    }
}