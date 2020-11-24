package com.epam.esm.giftcertificatemodule3.rest.controller;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.User;
import com.epam.esm.giftcertificatemodule3.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule3.services.OrderService;
import com.epam.esm.giftcertificatemodule3.services.UserService;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class OrderController {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private static final String ORDERS_BY_ID = "ordersById";
    private static final String ALL_ORDERS = "allOrders";

    private OrderService orderService;
    private UserService userService;
    private GiftCertificateService giftCertificateService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, GiftCertificateService giftCertificateService) {
        this.orderService = orderService;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping("/orders")
    public Order save(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "certificateId") List<Long> giftCertificateId
    ) {
        Order order = new Order();
        try {
            User user = userService.findById(userId);
            if (user == null) {
                LOGGER.error("User id=: " + userId);
                throw new IllegalArgumentException("User");
            }
            double price = 0;
            Set<GiftCertificate> certificates = new HashSet<>();
            for (Long id : giftCertificateId) {
                GiftCertificate giftCertificate = giftCertificateService.findById(id);
                if (giftCertificate == null) {
                    LOGGER.error("GiftCertificate id=: " + id);
                    throw new IllegalArgumentException("Certificates");
                }
                price += giftCertificate.getPrice();
                certificates.add(giftCertificate);
            }
            order.setPrice(price);
            order.setUser(user);
            order.setCertificates(certificates);
            orderService.save(order);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return order;
    }

    @GetMapping("/orders")
    public List<EntityModel<Order>> findAll(
            @RequestParam(defaultValue = "0") int firstResult,
            @RequestParam(defaultValue = "5") int maxResults
    ) {
        List<Order> returnObject;
        try {
            returnObject = orderService.findAll(firstResult, maxResults);
            if (returnObject == null || returnObject.isEmpty()) {
                throw new IllegalArgumentException("Orders");
            }
        } catch (ServiceException e) {
            LOGGER.error("findAll error: " + e.getMessage());
            throw new RuntimeException();
        }
        return getEntityModels(returnObject);
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> findById(@PathVariable Long id) {
        EntityModel<Order> returnObject;
        try {
            Order order = orderService.findById(id);
            if (order == null) {
                throw new IllegalArgumentException("Order");
            }
            returnObject = EntityModel.of(order);
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
        }
        return getEntityModel(returnObject);
    }

    private List<EntityModel<Order>> getEntityModels(List<Order> returnObject) {
        return returnObject.stream()
                .map(e -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(this.getClass()).findById(e.getId()));
                    EntityModel<Order> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel(ORDERS_BY_ID));
                    return entityModel;
                })
                .collect(Collectors.toList());
    }

    private EntityModel<Order> getEntityModel(EntityModel<Order> returnObject) {
        WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(FIRST_RESULT, MAX_RESULTS));
        return returnObject.add(linkToFindAll.withRel(ALL_ORDERS));
    }
}
