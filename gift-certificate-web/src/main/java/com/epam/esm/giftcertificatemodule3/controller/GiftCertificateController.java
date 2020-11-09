package com.epam.esm.giftcertificatemodule3.controller;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
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
    public List<GiftCertificate> findAll(
            @RequestParam(defaultValue = "0") int firstResult,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        List<GiftCertificate> giftCertificates;
        try {
            giftCertificates = giftCertificateService.findAll(firstResult, maxResults);
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

    @GetMapping("/certificates/findBy")
    public List<GiftCertificate> findBy(@RequestParam(required = false) Long id,
                                        @RequestParam(required = false) String tagName,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) String sortBy,
                                        @RequestParam(required = false) String sortOrder) {
        List<GiftCertificate> returnObject;

        SearchParametersHolder searchParametersHolder = new SearchParametersHolder();
        searchParametersHolder.setId(id);
        searchParametersHolder.setTagName(tagName);
        searchParametersHolder.setName(name);
        searchParametersHolder.setDescription(description);
        searchParametersHolder.setSortBy(sortBy);
        searchParametersHolder.setSortOrder(sortOrder);

        try {
            returnObject = giftCertificateService.findBy(searchParametersHolder);
        } catch (ServiceException e) {
            LOGGER.error("searchBy error: " + e.getMessage());
            throw new RuntimeException();
        }
        if (returnObject.isEmpty()) {
            throw new RuntimeException("Certificates");
        }
        return returnObject;
    }

    @PostMapping("/certificates")
    public GiftCertificate save(@RequestBody GiftCertificate giftCertificate) {
        try {
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
            giftCertificateService.update(giftCertificate);
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
