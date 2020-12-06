package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.Role;

/**
 * The interface interface provides methods to work with Role's information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface RoleService extends CrudDAO<Role, Long> {
    /**
     * Returns the instance of the Role type.
     *
     * @param name is a name of Role.
     * @return the instance of Role.
     */
    Role findByName(String name);
}
