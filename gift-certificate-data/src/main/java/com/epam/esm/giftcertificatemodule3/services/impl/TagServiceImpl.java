package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.services.TagService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Transactional
    @Override
    public List<Tag> findAll(int firstResult, int maxResults) throws ServiceException {
        return tagDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Tag findById(Integer id) throws ServiceException {
        return tagDAO.findById(id);
    }

    @Transactional
    @Override
    public Tag findByName(String name) throws ServiceException {
        return tagDAO.findByName(name);
    }

    @Transactional
    @Override
    public void save(Tag object) throws ServiceException {
        tagDAO.save(object);
    }

    @Transactional
    @Override
    public void update(Tag object) throws ServiceException {
        tagDAO.update(object);
    }

    @Transactional
    @Override
    public void delete(Tag object) throws ServiceException {
        tagDAO.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) throws ServiceException {
        tagDAO.deleteById(id);
    }
}
