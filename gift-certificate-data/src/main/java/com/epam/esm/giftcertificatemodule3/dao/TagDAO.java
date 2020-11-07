package com.epam.esm.giftcertificatemodule3.dao;

import com.epam.esm.giftcertificatemodule3.entity.Tag;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagDAO extends CrudDAO<Tag, Integer> {
    Tag findByName(String name);
}
