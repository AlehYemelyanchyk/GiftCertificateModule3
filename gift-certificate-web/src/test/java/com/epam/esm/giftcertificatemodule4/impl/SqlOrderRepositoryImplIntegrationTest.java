package com.epam.esm.giftcertificatemodule4.impl;

import com.epam.esm.giftcertificatemodule4.entity.Order;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class SqlOrderRepositoryImplIntegrationTest extends AbstractIntegrationTest {

    private Connection connection;

    @Autowired
    OrderService orderService;

    private static final Order TEST_ORDER = new Order();
    private static final User TEST_USER = new User();
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Test user";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 25;

    @BeforeEach
    void create() throws SQLException {
        TEST_ORDER.setPrice(new BigDecimal(10.99));
        TEST_ORDER.setDate(ZonedDateTime.now().toOffsetDateTime());
        TEST_ORDER.setUser(TEST_USER);

        TEST_USER.setId(TEST_ID);
        TEST_USER.setName(TEST_NAME);
        connection = executeSqlStartScript();
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlEndScript(connection);
    }

    @Test
    void saveTest() {
        List<Order> listBefore = orderService.findAll(FIRST_RESULT, MAX_RESULTS);
        orderService.save(TEST_ORDER);
        List<Order> listAfter = orderService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(listBefore);
        assertNotNull(listAfter);
        assertNotEquals(listBefore, listAfter);
    }

    @Test
    void findAllTest() {
        List<Order> actualList = orderService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        Order actual = orderService.findById(TEST_ID);
        assertEquals(TEST_ORDER.getId(), actual.getId());
    }

    @Test
    void updateTest() {
        Order beforeUpdate = orderService.findById(TEST_ID);
        TEST_ORDER.setId(TEST_ID);
        TEST_ORDER.setPrice(new BigDecimal(0.99));
        orderService.update(TEST_ORDER);
        Order afterUpdate = orderService.findById(TEST_ID);
        assertNotNull(beforeUpdate);
        assertNotNull(afterUpdate);
        assertNotEquals(beforeUpdate.getPrice(), afterUpdate.getPrice());
    }

    @Test
    void deleteTest() {
        // Not used method
    }

    @Test
    void deleteByIdTest() {
        orderService.deleteById(TEST_ID);
        Order actual = orderService.findById(TEST_ORDER.getId());
        assertNull(actual);
    }
}