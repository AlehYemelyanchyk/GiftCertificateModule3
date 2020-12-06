package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule4.dao.TagDAO;
import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule4.services.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Transactional
    @Override
    public List<GiftCertificate> findAll(int firstResult, int maxResults) {
        firstResult = Math.max(firstResult, 0);
        maxResults = Math.max(maxResults, 5);
        return giftCertificateDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateDAO.findById(id);
    }

    @Transactional
    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder,
                                        int firstResult, int maxResults) {
        firstResult = Math.max(firstResult, 0);
        maxResults = Math.max(maxResults, 5);
        return giftCertificateDAO.findBy(searchParametersHolder, firstResult, maxResults);
    }

    @Transactional
    @Override
    public void save(GiftCertificate giftCertificate) {
        getTagsId(giftCertificate);
        giftCertificateDAO.save(giftCertificate);
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) {
        getTagsId(giftCertificate);
        giftCertificateDAO.update(giftCertificate);
    }

    @Transactional
    @Override
    public void delete(GiftCertificate giftCertificate) {
        giftCertificateDAO.delete(giftCertificate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        giftCertificateDAO.deleteById(id);
    }

    private void getTagsId(GiftCertificate giftCertificate) {
        Set<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            tags.forEach(tag -> {
                Tag savedTag = null;
                try {
                    savedTag = tagDAO.findByName(tag.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                    // this tag will be saved later.
                }
                if (savedTag != null) {
                    tag.setId(savedTag.getId());
                }
            });
        }
    }
}
