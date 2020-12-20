package com.epam.esm.giftcertificatemodule4.services.impl;


import com.epam.esm.giftcertificatemodule4.dao.UserRepository;
import com.epam.esm.giftcertificatemodule4.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepository.findByName(username);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(e);
            throw new UsernameNotFoundException("User with name " + username + " Not Found");
        }
        return UserDetailsImpl.build(user);
    }
}
