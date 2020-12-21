package com.epam.esm.giftcertificatemodule4.services;


import com.epam.esm.giftcertificatemodule4.entity.User;

/**
 * The interface provides methods to work with Users information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface UserService extends CrudService<User, Long> {
    /**
     * Returns the instance of the User type.
     *
     * @param id is an ID of User.
     * @return the instance of User.
     */
    User findById(String id);

    /**
     * Returns the instance of the User type.
     *
     * @param name is a name of User.
     * @return the instance of User.
     */
    User findByName(String name) ;

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
}
