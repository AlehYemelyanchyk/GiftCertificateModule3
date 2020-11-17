package com.epam.esm.giftcertificatemodule3.services;


import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;

import java.util.List;

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

    /**
     * Returns all instances of the Order type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @param firstResult is the position of the first result in the datasource which should be returned.
     * @param maxResults is the number of the results by one page.
     * @return List with all entities found with the search.
     */
    List<Tag> findByHighestUserExpense(SearchParametersHolder searchParametersHolder, int firstResult, int maxResults);
}
