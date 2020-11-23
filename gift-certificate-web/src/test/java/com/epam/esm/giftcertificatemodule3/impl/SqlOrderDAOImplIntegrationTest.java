package com.epam.esm.giftcertificatemodule3.impl;

import com.epam.esm.giftcertificatemodule3.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SqlOrderDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    OrderDAO orderDAO;

    private static final Order TEST_ORDER = new Order();
    private static final User TEST_USER = new User();
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Test user";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @BeforeEach
    void create() throws SQLException {
        TEST_ORDER.setId(TEST_ID);
        TEST_ORDER.setPrice(9.99d);
        TEST_ORDER.setDate(ZonedDateTime.now().toOffsetDateTime());
        TEST_ORDER.setUser(TEST_USER);

        TEST_USER.setId(TEST_ID);
        TEST_USER.setName(TEST_NAME);
        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void saveTest() throws SQLException {
        Connection connection = getConnection();
        List<Order> listBefore = orderDAO.findAll(FIRST_RESULT, 30);
        orderDAO.save(TEST_ORDER);
        List<Order> listAfter = orderDAO.findAll(FIRST_RESULT, 30);
        assertNotNull(listBefore);
        assertNotNull(listAfter);
        assertNotEquals(listBefore, listAfter);
        connection.close();
    }

    @Test
    void findAllTest() {
        List<Order> actualList = orderDAO.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        Order actual = orderDAO.findById(TEST_ID);
        assertEquals(TEST_ORDER.getId(), actual.getId());
    }

    @Test
    void updateTest() throws SQLException {
        Connection connection = getConnection();
        Order beforeUpdate = orderDAO.findById(TEST_ID);
        TEST_ORDER.setPrice(0.99d);
        orderDAO.update(TEST_ORDER);
        Order afterUpdate = orderDAO.findById(TEST_ID);
        assertNotNull(beforeUpdate);
        assertNotNull(afterUpdate);
        assertNotEquals(beforeUpdate.getPrice(), afterUpdate.getPrice());
        connection.close();
    }

    @Test
    void deleteTest() {
        orderDAO.save(TEST_ORDER);
        Order beforeDelete = orderDAO.findById(TEST_ORDER.getId());
        orderDAO.delete(TEST_ORDER);
        Order afterDelete = orderDAO.findById(TEST_ORDER.getId());
        assertNotEquals(beforeDelete, afterDelete);
    }

    @Test
    void deleteByIdTest() {
        orderDAO.deleteById(TEST_ID);
        Order actual = orderDAO.findById(TEST_ORDER.getId());
        assertNull(actual);
    }
}