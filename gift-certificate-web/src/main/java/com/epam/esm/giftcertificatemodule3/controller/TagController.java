package com.epam.esm.giftcertificatemodule3.controller;

import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.services.TagService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    private static final Logger LOGGER = LogManager.getLogger();

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<Tag> findAll() {
        List<Tag> tags;

        try {
            tags = tagService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return tags;
    }

    @GetMapping("/tags/{id}")
    public Tag findById(@PathVariable int id) {
        Tag returnObject;
        try {
            returnObject = tagService.findById(id);
            if (returnObject == null) {
                throw new RuntimeException();
            }
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return returnObject;
    }

    @PostMapping("/tags")
    public Tag saveTag(@RequestBody Tag tag) {
        try {
            tagService.save(tag);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return tag;
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
