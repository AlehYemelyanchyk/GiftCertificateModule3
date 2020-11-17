package com.epam.esm.giftcertificatemodule3.rest.controller;

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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TagController {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private static final String CERTIFICATES_BY_ID = "certificateById";
    private static final String TAGS_BY_ID = "tagsById";
    private static final String ALL_TAGS = "allTags";

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
        return returnObject.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(this.getClass()).findById(e.getId()));
                    EntityModel<Tag> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel(TAGS_BY_ID));
                    return entityModel;
                })
                .collect(Collectors.toList());
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
        WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(FIRST_RESULT, MAX_RESULTS));
        returnObject.add(linkToFindAll.withRel(ALL_TAGS));
        return returnObject;
    }

    @GetMapping("/tags/findHighestPrice")
    public List<EntityModel<Tag>> findByHighestUserExpense(
            @RequestParam(defaultValue = "0") int firstResult,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        List<Tag> tags;
        SearchParametersHolder searchParametersHolder = new SearchParametersHolder();
        try {
            tags = tagService.findByHighestUserExpense(searchParametersHolder, firstResult, maxResults);
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return tags.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById =
                            linkTo(methodOn(TagController.class).findById(e.getId()));
                    EntityModel<Tag> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel(TAGS_BY_ID));
                    return entityModel;
                })
                .collect(Collectors.toList());
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
