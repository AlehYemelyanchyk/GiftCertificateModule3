package com.epam.esm.giftcertificatemodule4.rest.controller;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule4.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule4.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController extends AbstractController<GiftCertificate> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String RESULT_BY_ID = "certificateById";
    private static final String ALL_RESULTS = "allCertificates";

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public GiftCertificate save(@RequestBody GiftCertificate giftCertificate) {
        try {
            giftCertificateService.save(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return giftCertificate;
    }

    @PreAuthorize("hasRole('GUEST') or hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("")
    public List<EntityModel<GiftCertificate>> findAll(
            @RequestParam(defaultValue = "0") int firstResult,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        List<GiftCertificate> returnObject;
        try {
            returnObject = giftCertificateService.findAll(firstResult, maxResults);
            if (returnObject == null || returnObject.isEmpty()) {
                throw new IllegalArgumentException("Gift Certificates");
            }
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }

        return getEntityModels(returnObject);
    }

    @PreAuthorize("hasRole('GUEST') or hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public EntityModel<GiftCertificate> findById(@PathVariable Long id) {
        GiftCertificate returnObject;
        try {
            returnObject = giftCertificateService.findById(id);
            if (returnObject == null) {
                throw new IllegalArgumentException("Gift Certificate " + id);
            }
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return getEntityModel(returnObject);
    }

    @PreAuthorize("hasRole('GUEST') or hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/findBy")
    public List<EntityModel<GiftCertificate>> findBy(@RequestParam(defaultValue = "0") int firstResult,
                                                     @RequestParam(defaultValue = "5") int maxResults,
                                                     @RequestParam(required = false) Long id,
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
            returnObject = giftCertificateService.findBy(searchParametersHolder, firstResult, maxResults);
        } catch (ServiceException e) {
            LOGGER.error("searchBy error: " + e.getMessage());
            throw new RuntimeException();
        }
        if (returnObject.isEmpty()) {
            throw new IllegalArgumentException("Gift Certificates");
        }
        return getEntityModels(returnObject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public GiftCertificate update(@RequestBody GiftCertificate giftCertificate) {
        try {
            giftCertificateService.update(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return giftCertificate;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("")
    public void delete(@RequestBody GiftCertificate giftCertificate) {
        try {
            giftCertificateService.delete(giftCertificate);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new RuntimeException("GiftCertificate (giftCertificate = " + giftCertificate.getName() + ")");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            giftCertificateService.deleteById(id);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new RuntimeException("GiftCertificate (id = " + id + ")");
        }
    }

    @Override
    public String getResultById() {
        return RESULT_BY_ID;
    }

    @Override
    public String getAllResults() {
        return ALL_RESULTS;
    }
}
