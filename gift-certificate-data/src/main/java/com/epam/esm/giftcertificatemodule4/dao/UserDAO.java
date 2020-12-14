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
     * Returns true if a User with the given name exists.
     *
     * @param name is a name of User.
     * @return true or false.
     */
    Boolean existsByName(String name);

    /**
     * Returns true if a User with the given email exists.
     *
     * @param email is an email of User.
     * @return true or false.
     */
    Boolean existsByEmail(String email);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     */
    User findById(String id);
}
