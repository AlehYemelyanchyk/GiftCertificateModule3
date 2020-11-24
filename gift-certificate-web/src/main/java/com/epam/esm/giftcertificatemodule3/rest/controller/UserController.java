package com.epam.esm.giftcertificatemodule3.rest.controller;

import com.epam.esm.giftcertificatemodule3.entity.User;
import com.epam.esm.giftcertificatemodule3.services.UserService;
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
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private static final String USERS_BY_ID = "usersById";
    private static final String ALL_USERS = "allUsers";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<EntityModel<User>> findAll(
            @RequestParam(defaultValue = "0") int firstResult,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        List<User> returnObject;
        try {
            returnObject = userService.findAll(firstResult, maxResults);
            if (returnObject == null || returnObject.isEmpty()) {
                throw new IllegalArgumentException("Users");
            }
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return getEntityModels(returnObject);
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findById(@PathVariable long id) {
        EntityModel<User> returnObject = null;
        try {
            User user = userService.findById(id);
            if (user == null) {
                throw new IllegalArgumentException("User " + id);
            }
            returnObject = EntityModel.of(user);
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
        }
        return getEntityModel(returnObject);
    }

    private List<EntityModel<User>> getEntityModels(List<User> returnObject) {
        return returnObject.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(this.getClass()).findById(e.getId()));
                    EntityModel<User> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel(USERS_BY_ID));
                    return entityModel;
                })
                .collect(Collectors.toList());
    }

    private EntityModel<User> getEntityModel(EntityModel<User> returnObject) {
        WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(FIRST_RESULT, MAX_RESULTS));
        return returnObject.add(linkToFindAll.withRel(ALL_USERS));
    }
}
