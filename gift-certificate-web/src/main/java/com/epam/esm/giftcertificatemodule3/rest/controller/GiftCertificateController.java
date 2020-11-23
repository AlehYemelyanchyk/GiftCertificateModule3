package com.epam.esm.giftcertificatemodule3.rest.controller;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule3.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private static final String CERTIFICATES_BY_ID = "certificateById";
    private static final String ALL_CERTIFICATES = "allCertificates";

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
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

    @GetMapping("/certificates")
    public List<EntityModel<GiftCertificate>> findAll(
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

        return getEntityModels(giftCertificates);
    }

    @GetMapping("/certificates/{id}")
    public EntityModel<GiftCertificate> findById(@PathVariable Long id) {
        EntityModel<GiftCertificate> returnObject;
        try {
            returnObject = EntityModel.of(giftCertificateService.findById(id));
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(FIRST_RESULT, MAX_RESULTS));
        returnObject.add(linkToFindAll.withRel(ALL_CERTIFICATES));
        return returnObject;
    }

    @GetMapping("/certificates/findBy")
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
            throw new IllegalArgumentException("Certificates");
        }
        return getEntityModels(returnObject);
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

    private List<EntityModel<GiftCertificate>> getEntityModels(List<GiftCertificate> returnObject) {
        return returnObject.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(this.getClass()).findById(e.getId()));
                    EntityModel<GiftCertificate> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel(CERTIFICATES_BY_ID));
                    return entityModel;
                })
                .collect(Collectors.toList());
    }
}
