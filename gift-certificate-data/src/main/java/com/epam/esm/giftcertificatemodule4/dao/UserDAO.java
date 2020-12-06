package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.User;

/**
 * The interface interface provides methods to work with Users information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface UserDAO extends CrudDAO<User, Long> {
    /**
     * Returns the instance of the User type.
     *
     * @param name is a name of User.
     * @return the instance of User.
     */
    User findByName(String name);

    /**
     * Returns the instance of the User type.
     *
     * @param email is a name of User.
     * @return the instance of User.
     */
    User findByEmail(String email);
}
