package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    /**
     * Returns the instance of the GiftCertificate type.
     *
     * @param name is a name of GiftCertificate.
     * @return the instance of GiftCertificate.
     */
    GiftCertificate findByName(String name);
}
