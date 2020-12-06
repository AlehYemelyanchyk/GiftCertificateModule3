package com.epam.esm.giftcertificatemodule4.security.services;


import com.epam.esm.giftcertificatemodule4.dao.UserDAO;
import com.epam.esm.giftcertificatemodule4.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " Not Found");
        }
        return UserDetailsImpl.build(user);
    }
}
