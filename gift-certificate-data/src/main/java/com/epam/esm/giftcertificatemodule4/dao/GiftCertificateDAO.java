package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;

import java.util.List;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface GiftCertificateDAO extends CrudDAO<GiftCertificate, Long> {
    /**
     * Returns all instances of the GiftCertificate type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @param firstResult is the position of the first result in the datasource which should be returned.
     * @param maxResults is the number of the results by one page.
     * @return List with all entities found with the search.
     */
    List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder, int firstResult, int maxResults);
}
