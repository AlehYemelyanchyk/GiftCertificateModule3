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
     * @return all entities.
     */
    List<T> findAll();

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     */
    T findById(ID id);

    /**
     * Saves a given entity if id is equals to 0.
     * Updates a given entity if id is not equals to 0.
     *
     * @param object must not be {@literal null}.
     */
    void save(T object);

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