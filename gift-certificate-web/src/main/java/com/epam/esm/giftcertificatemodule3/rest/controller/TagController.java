package com.epam.esm.giftcertificatemodule3.rest.controller;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule3.services.OrderService;
import com.epam.esm.giftcertificatemodule3.services.TagService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TagController {

    private static final Logger LOGGER = LogManager.getLogger();

    private TagService tagService;
    private OrderService orderService;

    @Autowired
    public TagController(TagService tagService, OrderService orderService) {
        this.tagService = tagService;
        this.orderService = orderService;
    }

    @PostMapping("/tags")
    public Tag save(@RequestBody Tag tag) {
        try {
            tagService.save(tag);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return tag;
    }

    @GetMapping("/tags")
    public List<EntityModel<Tag>> findAll(
            @RequestParam(defaultValue = "0") int firstResult,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        List<Tag> returnObject;
        try {
            returnObject = tagService.findAll(firstResult, maxResults);
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        List<EntityModel<Tag>> collect = returnObject.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(this.getClass()).findById(e.getId()));
                    EntityModel<Tag> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel("tagsById"));
                    return entityModel;
                })
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/tags/{id}")
    public EntityModel<Tag> findById(@PathVariable int id) {
        EntityModel<Tag> returnObject;
        try {
            returnObject = EntityModel.of(tagService.findById(id));
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(0, 5));
        returnObject.add(linkToFindAll.withRel("allTags"));
        return returnObject;
    }

    @GetMapping("/tags/findByName")
    public List<EntityModel<GiftCertificate>> findByName(@RequestParam String name) {
        List<GiftCertificate> certificates;
        try {
            certificates = tagService.findByName(name).getCertificates();
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        List<EntityModel<GiftCertificate>> collect = certificates.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(GiftCertificateController.class).findById(e.getId()));
                    EntityModel<GiftCertificate> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel("certificateById"));
                    return entityModel;
                })
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/tags/findMostUsed")
    public EntityModel<Tag> findMostUsed() {
        List<Order> orders;
        Tag tag = null;
        int count = 0;
        SearchParametersHolder searchParametersHolder = new SearchParametersHolder();
        searchParametersHolder.setHighestCost(true);
        try {
            orders = orderService.findBy(searchParametersHolder);
            List<Tag> tags = orders.stream()
                    .flatMap(order -> order.getCertificates().stream())
                    .flatMap(giftCertificate -> giftCertificate.getTags().stream())
                    .collect(Collectors.toList());
            Map<Tag, Integer> tagsMap = new HashMap<>();
            tags.forEach(e -> {
                Integer tagCount = (tagsMap.get(e) == null) ? 0 : tagsMap.get(e);
                tagsMap.put(e, ++tagCount);
            });
            for (Map.Entry<Tag, Integer> entry : tagsMap.entrySet()) {
                Integer value = entry.getValue();
                if (value > count) {
                    count = value;
                    tag = entry.getKey();
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        WebMvcLinkBuilder linkToFindById = linkTo(methodOn(TagController.class).findById(tag.getId()));
        EntityModel<Tag> entityModel = EntityModel.of(tag);
        entityModel.add(linkToFindById.withRel("certificateById"));
        return entityModel;
    }

    @PutMapping("/tags")
    public Tag update(@RequestBody Tag tag) {
        try {
            tagService.update(tag);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return tag;
    }

    @DeleteMapping("/tags")
    public void delete(@RequestBody Tag tag) {
        try {
            tagService.delete(tag);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new RuntimeException("Tag (tag = " + tag.getName() + ")");
        }
    }

    @DeleteMapping("/tags/{id}")
    public void deleteById(@PathVariable Integer id) {
        try {
            tagService.deleteById(id);
        } catch (ServiceException e) {
            LOGGER.error("delete error: " + e.getMessage());
            throw new RuntimeException("Tag (id = " + id + ")");
        }
    }
}
