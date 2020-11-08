package com.epam.esm.giftcertificatemodule3.services;

import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;

import java.util.List;

/**
 * The interface provides methods to work with CrudService operations.
 *
 * @author Aleh Yemelyanchyk
 */
public interface CrudService<T, ID> {
    /**
     * Returns all instances of the type.
     *
     * @param firstResult is the position of the first result in the datasource which should be returned.
     * @param maxResults is the number of the results by one page.
     * @return all entities
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    List<T> findAll(int firstResult, int maxResults) throws ServiceException;

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    T findById(ID id) throws ServiceException;

    /**
     * Saves a given entity.
     *
     * @param object must not be {@literal null}.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    void save(T object) throws ServiceException;

    /**
     * Updates a given entity.
     *
     * @param object must not be {@literal null}.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    void update(T object) throws ServiceException;

    /**
     * Deletes a given entity.
     *
     * @param object must not be {@literal null}.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    void delete(T object) throws ServiceException;

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method
     */
    void deleteById(ID id) throws ServiceException;
}