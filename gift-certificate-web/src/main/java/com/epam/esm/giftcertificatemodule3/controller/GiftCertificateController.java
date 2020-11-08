package com.epam.esm.giftcertificatemodule3.controller;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule3.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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
            @RequestParam int firstResult,
            @RequestParam int maxResults
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
    public List<GiftCertificate> findBy(@RequestParam Optional<Long> id,
                                              @RequestParam Optional<String> tagName,
                                              @RequestParam Optional<String> name,
                                              @RequestParam Optional<String> description,
                                              @RequestParam Optional<String> sortBy,
                                              @RequestParam Optional<String> sortOrder) {
        List<GiftCertificate> returnObject;

        SearchParametersHolder searchParametersHolder = new SearchParametersHolder();
        searchParametersHolder.setId(id.orElse(null));
        searchParametersHolder.setTagName(tagName.orElse(null));
        searchParametersHolder.setName(name.orElse(null));
        searchParametersHolder.setDescription(description.orElse(null));
        searchParametersHolder.setSortBy(sortBy.orElse(null));
        searchParametersHolder.setSortOrder(sortOrder.orElse(null));

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

    private OffsetDateTime formatDate() {
        return ZonedDateTime.now().toOffsetDateTime();
    }
}
