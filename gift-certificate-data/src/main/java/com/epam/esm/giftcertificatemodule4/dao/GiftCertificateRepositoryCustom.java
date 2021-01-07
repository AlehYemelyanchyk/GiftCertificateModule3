package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface GiftCertificateRepositoryCustom {
    /**
     * Returns all instances of the GiftCertificate type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @param pageable
     * @return List with all entities found with the search.
     */
    List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder, Pageable pageable);
}
