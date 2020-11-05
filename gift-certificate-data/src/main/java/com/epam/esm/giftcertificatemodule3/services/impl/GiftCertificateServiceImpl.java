package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
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
    public void save(GiftCertificate object) throws ServiceException {
        giftCertificateDAO.save(object);
    }

    @Transactional
    @Override
    public void delete(GiftCertificate object) throws ServiceException {
        giftCertificateDAO.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ServiceException {
        giftCertificateDAO.deleteById(id);
    }
}
