package com.epam.esm.giftcertificatemodule3.dao;

import java.util.List;

/**
 * The interface provides methods to work with CrudDAO operations.
 *
 * @author Aleh Yemelyanchyk
 */
public interface CrudDAO<T, ID> {

    /**
     * Returns all instances of the type.
     *
     * @param firstResult is the position of the first result in the datasource which should be returned.
     * @param maxResults is the number of the results by one page.
     * @return all entities.
     */
    List<T> findAll(int firstResult, int maxResults);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     */
    T findById(ID id);

    /**
     * Saves a given entity.
     *
     * @param object must not be {@literal null}.
     */
    void save(T object);

    /**
     * Updates a given entity.
     *
     * @param object must not be {@literal null}.
     */
    void update(T object);

    /**
     * Deletes a given entity.
     *
     * @param object must not be {@literal null}.
     */
    void delete(T object);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     */
    void deleteById(ID id);
}