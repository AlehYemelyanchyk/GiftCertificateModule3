package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface provides methods to work with Orders information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
