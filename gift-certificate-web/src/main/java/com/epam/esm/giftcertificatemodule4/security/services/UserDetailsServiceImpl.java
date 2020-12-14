package com.epam.esm.giftcertificatemodule4.security.services;


import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.UserService;
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

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(e);
            throw new UsernameNotFoundException("User #" + id + " Not Found");
        }
        return UserDetailsImpl.build(user);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findByName(username);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(e);
            throw new UsernameNotFoundException("User with name " + username + " Not Found");
        }
        return UserDetailsImpl.build(user);
    }
}
