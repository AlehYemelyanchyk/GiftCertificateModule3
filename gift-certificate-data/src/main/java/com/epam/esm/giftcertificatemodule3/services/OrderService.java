package com.epam.esm.giftcertificatemodule3.services;


import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;

import java.util.List;

/**
 * The interface provides methods to work with Orders information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface OrderService extends CrudService<Order, Long> {
    /**
     * Returns all instances of the GiftCertificate type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @return List with all entities found with the search.
     */
    List<Order> findHighestPriceByUser(SearchParametersHolder searchParametersHolder);
}
