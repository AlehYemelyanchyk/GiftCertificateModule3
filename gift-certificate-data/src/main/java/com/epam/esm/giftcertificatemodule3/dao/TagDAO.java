package com.epam.esm.giftcertificatemodule3.dao;

import com.epam.esm.giftcertificatemodule3.entity.Tag;

import java.util.List;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagDAO extends CrudDAO<Tag, Long> {
    /**
     * Returns the instance of the Tag type.
     *
     * @param name is a name of Tag.
     * @return the instance of Tag.
     */
    Tag findByName(String name);

    /**
     * Returns all instances of the Tag type.
     *
     * @param firstResult            is the position of the first result in the datasource which should be returned.
     * @param maxResults             is the number of the results by one page.
     * @return List with all entities found with the search.
     */
    List<Tag> findMostPopularTags(int firstResult, int maxResults);
}
