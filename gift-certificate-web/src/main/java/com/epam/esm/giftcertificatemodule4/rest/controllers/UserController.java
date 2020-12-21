package com.epam.esm.giftcertificatemodule4.rest.controllers;

import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.UserService;
import com.epam.esm.giftcertificatemodule4.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("hasRole('ADMIN') " +
//        "or hasAuthority('SCOPE_administrate')")
@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractController<User> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String RESULT_BY_ID = "usersById";
    private static final String ALL_RESULTS = "allUsers";

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<EntityModel<User>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<User> returnObject;
        try {
            returnObject = userService.findAll(page, size);
            if (returnObject == null || returnObject.isEmpty()) {
                throw new IllegalArgumentException("Users");
            }
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return getEntityModels(returnObject);
    }

    @Override
    public EntityModel<User> findById(Long id) {
        return null;
    }

    @PreAuthorize("hasAnyRole('ADMIN') or #id == principal.attributes.get(\"sub\") " +
            "or hasAnyAuthority('SCOPE_administrate')")
    @GetMapping("/{id}")
    public EntityModel<User> findById(@PathVariable String id,
                                      @AuthenticationPrincipal OidcUser principal) {
        ;
        User returnObject = null;
        try {
            returnObject = userService.findById(id);
            if (returnObject == null) {
                throw new IllegalArgumentException("User " + id);
            }
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
        }
        return getEntityModel(returnObject);
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
