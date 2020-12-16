package com.epam.esm.giftcertificatemodule4.dao;

import com.epam.esm.giftcertificatemodule4.entity.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface provides custom methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagRepositoryCustom {
    /**
     * Returns all instances of the Tag type.
     *
     * @param pageable
     * @return List with all entities found with the search.
     */
    List<Tag> findMostPopularTags(Pageable pageable);
}
