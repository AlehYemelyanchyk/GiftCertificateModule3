package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.services.TagService;
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
    public List<Tag> findAll(int firstResult, int maxResults) {
        return tagDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Tag findById(Integer id) {
        return tagDAO.findById(id);
    }

    @Transactional
    @Override
    public Tag findByName(String name) {
        Tag tag = tagDAO.findByName(name);
        if (tag != null) {
            tag.getCertificates();
        }
        return tag;
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
    public void deleteById(Integer id) {
        tagDAO.deleteById(id);
    }
}
