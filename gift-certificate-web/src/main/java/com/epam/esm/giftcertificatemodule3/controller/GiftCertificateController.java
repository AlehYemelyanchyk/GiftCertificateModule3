package com.epam.esm.giftcertificatemodule3.controller;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    private static final Logger LOGGER = LogManager.getLogger();

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/certificates")
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates;

        try {
            giftCertificates = giftCertificateService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return giftCertificates;
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        GiftCertificate returnObject;
        try {
            returnObject = giftCertificateService.findById(id);
            if (returnObject == null) {
                throw new RuntimeException();
            }
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @PostMapping("/certificates")
    public GiftCertificate saveGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        try {
            giftCertificate.setId(0L);
            giftCertificateService.save(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return giftCertificate;
    }

    @PutMapping("/certificates")
    public GiftCertificate update(@RequestBody GiftCertificate giftCertificate) {
        try {
            giftCertificateService.save(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return giftCertificate;
    }

    @DeleteMapping("/certificates")
    public void delete(@RequestBody GiftCertificate giftCertificate) {
        try {
            giftCertificateService.delete(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new RuntimeException("GiftCertificate (giftCertificate = " + giftCertificate.getName() + ")");
        }
    }

    @DeleteMapping("/certificates/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            giftCertificateService.deleteById(id);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new RuntimeException("GiftCertificate (id = " + id + ")");
        }
    }
}
