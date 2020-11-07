package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<GiftCertificate> findAll() throws ServiceException {
        return giftCertificateDAO.findAll();
    }

    @Transactional
    @Override
    public GiftCertificate findById(Long id) throws ServiceException {
        return giftCertificateDAO.findById(id);
    }

    @Transactional
    @Override
    public void save(GiftCertificate giftCertificate) throws ServiceException {
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> {
            Tag savedTag = tagDAO.findByName(tag.getName());
            if (savedTag != null) {
                tag.setId(savedTag.getId());
            }
        });
        giftCertificateDAO.save(giftCertificate);
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) throws ServiceException {
        giftCertificateDAO.update(giftCertificate);
    }

    @Transactional
    @Override
    public void delete(GiftCertificate giftCertificate) throws ServiceException {
        giftCertificateDAO.delete(giftCertificate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ServiceException {
        giftCertificateDAO.deleteById(id);
    }
}
