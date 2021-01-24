package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserRepository;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 5);
        Pageable paging = PageRequest.of(page, size);
        return userRepository.findAll(paging).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void save(User object) {
        userRepository.save(object);
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
