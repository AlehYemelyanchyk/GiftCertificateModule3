package com.epam.esm.giftcertificatemodule3.dao;

import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;

import java.util.List;

/**
 * The interface provides methods to work with Orders information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface OrderDAO extends CrudDAO<Order, Long> {
    /**
     * Returns all instances of the Order type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @param firstResult is the position of the first result in the datasource which should be returned.
     * @param maxResults is the number of the results by one page.
     * @return List with all entities found with the search.
     */
    List<Order> findByHighestUserExpense(SearchParametersHolder searchParametersHolder, int firstResult,
                                         int maxResults);
}
