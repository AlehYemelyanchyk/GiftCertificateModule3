package com.epam.esm.giftcertificatemodule3.services;


import com.epam.esm.giftcertificatemodule3.entity.Tag;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagService extends CrudService<Tag, Integer> {
    /**
     * Returns the instance of the Tag type.
     *
     * @param name is a name of Tag.
     * @return the instance of Tag.
     */
    Tag findByName(String name) ;
}
