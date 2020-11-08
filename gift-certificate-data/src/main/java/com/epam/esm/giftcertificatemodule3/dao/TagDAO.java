package com.epam.esm.giftcertificatemodule3.dao;

import com.epam.esm.giftcertificatemodule3.entity.Tag;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagDAO extends CrudDAO<Tag, Integer> {
    /**
     * Returns the instance of the Tag type.
     *
     * @param name is a name of Tag.
     * @return the instance of Tag.
     */
    Tag findByName(String name);
}
