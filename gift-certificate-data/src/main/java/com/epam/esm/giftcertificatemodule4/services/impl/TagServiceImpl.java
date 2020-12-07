package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.TagDAO;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Transactional
    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        firstResult = Math.max(firstResult, 0);
        maxResults = Math.max(maxResults, 5);
        return tagDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Tag findById(Long id) {
        return tagDAO.findById(id);
    }

    @Transactional
    @Override
    public Tag findByName(String name) {
        return tagDAO.findByName(name);
    }

    @Transactional
    @Override
    public List<Tag> findMostPopularTags(int firstResult, int maxResults) {
        firstResult = Math.max(firstResult, 0);
        maxResults = Math.max(maxResults, 1);
        return tagDAO.findMostPopularTags(firstResult, maxResults);
    }

    @Transactional
    @Override
    public void save(Tag object) {
        tagDAO.save(object);
    }

    @Transactional
    @Override
    public void update(Tag object) {
        tagDAO.update(object);
    }

    @Transactional
    @Override
    public void delete(Tag object) {
        tagDAO.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        tagDAO.deleteById(id);
    }
}
