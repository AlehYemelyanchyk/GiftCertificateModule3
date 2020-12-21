package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.User;

/**
 * The interface interface provides methods to work with Users information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface UserRepositoryCustom {
    /**
     * Returns the instance of the User type.
     *
     * @param id is an ID of User.
     * @return the instance of User.
     */
    User findById(String id);
}
