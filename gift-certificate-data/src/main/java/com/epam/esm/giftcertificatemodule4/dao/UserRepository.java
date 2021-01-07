package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface interface provides methods to work with Users information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface UserRepository extends JpaRepository<User, Long> {
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
}
