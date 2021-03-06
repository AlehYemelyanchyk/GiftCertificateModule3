package com.epam.esm.giftcertificatemodule4.services;


import com.epam.esm.giftcertificatemodule4.entity.Tag;

import java.util.List;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagService extends CrudService<Tag, Long> {
    /**
     * Returns the instance of the Tag type.
     *
     * @param name is a name of Tag.
     * @return the instance of Tag.
     */
    Tag findByName(String name) ;

    /**
     * Returns all instances of the Order type.
     *
     * @param page is the position of the first result in the datasource which should be returned.
     * @param size is the number of the results by one page.
     * @return List with all entities found with the search.
     */
    List<Tag> findMostPopularTags(int page, int size);
}
