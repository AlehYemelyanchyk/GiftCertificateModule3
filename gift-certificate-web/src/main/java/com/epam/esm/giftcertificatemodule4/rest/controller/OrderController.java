package com.epam.esm.giftcertificatemodule4.rest.controller;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Order;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule4.services.OrderService;
import com.epam.esm.giftcertificatemodule4.services.UserService;
import com.epam.esm.giftcertificatemodule4.services.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api")
public class OrderController extends AbstractController<Order> {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String RESULT_BY_ID = "ordersById";
    private static final String ALL_RESULTS = "allOrders";

    private OrderService orderService;
    private UserService userService;
    private GiftCertificateService giftCertificateService;

    public OrderController(OrderService orderService, UserService userService, GiftCertificateService giftCertificateService) {
        this.orderService = orderService;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @PreAuthorize("hasRole('USER')")
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
                    throw new IllegalArgumentException("Gift Certificate " + id);
                }
                price += giftCertificate.getPrice();
                certificates.add(giftCertificate);
            }
            order.setPrice(Math.round(price * 100.0) / 100.0);
            order.setUser(user);
            order.setCertificates(certificates);
            orderService.save(order);
        } catch (ServiceException e) {
            LOGGER.error("save error: " + e.getMessage());
            throw new RuntimeException();
        }
        return order;
    }

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/{id}")
    public EntityModel<Order> findById(@PathVariable Long id) {
        Order returnObject;
        try {
            returnObject = orderService.findById(id);
            if (returnObject == null) {
                throw new IllegalArgumentException("Order " + id);
            }
        } catch (ServiceException e) {
            LOGGER.error("findById error: " + e.getMessage());
            throw new RuntimeException();
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
