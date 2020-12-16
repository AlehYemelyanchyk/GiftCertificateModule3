package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface provides JpaRepository methods to work with Roles information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Returns the instance of the Role type.
     *
     * @param name is a name of Role.
     * @return the instance of Role.
     */
    Role findByName(String name);
}
