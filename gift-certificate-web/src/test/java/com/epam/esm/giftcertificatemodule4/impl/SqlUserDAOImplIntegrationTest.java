package com.epam.esm.giftcertificatemodule4.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserDAO;
import com.epam.esm.giftcertificatemodule4.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class SqlUserDAOImplIntegrationTest extends AbstractIntegrationTest {

    private Connection connection;

    @Autowired
    UserDAO userDAO;

    private static final User TEST_USER = new User();
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "Test user";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @BeforeEach
    void create() throws SQLException {
        TEST_USER.setId("TEST_ID");
        TEST_USER.setName(TEST_NAME);
        connection = executeSqlStartScript();
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlEndScript(connection);
    }

    @Test
    void findAllTest() {
        List<User> actualList = userDAO.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        User actual = userDAO.findById(TEST_ID);
        assertEquals(TEST_USER.getId(), actual.getId());
    }
}