package com.epam.esm.giftcertificatemodule3.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SqlTagDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TagDAO tagDAO;

    private static final Tag TEST_TAG = new Tag();
    private static final Tag EXPECTED_TAG = new Tag();
    private static final Long TEST_ID = 6L;
    private static final String TEST_NAME = "blue";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_TAG.setName("tag1");
        EXPECTED_TAG.setId(1L);
        TEST_TAG.setName(TEST_NAME);
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
        List<Tag> listBefore = tagDAO.findAll(FIRST_RESULT, 12);
        tagDAO.save(TEST_TAG);
        List<Tag> listAfter = tagDAO.findAll(FIRST_RESULT, 12);
        assertNotNull(listBefore);
        assertNotNull(listAfter);
        assertNotEquals(listBefore, listAfter);
        connection.close();
    }

    @Test
    void findAllTest() {
        List<Tag> actualList = tagDAO.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        Tag actual = tagDAO.findById(EXPECTED_TAG.getId());
        assertEquals(EXPECTED_TAG.getId(), actual.getId());
    }

    @Test
    void findByNameTest() {
        Tag actual = tagDAO.findByName(EXPECTED_TAG.getName());
        assertEquals(EXPECTED_TAG.getId(), actual.getId());
    }

    @Test
    void findMostPopularTagsTest() {
        List<Tag> actual = tagDAO.findMostPopularTags(FIRST_RESULT, MAX_RESULTS);
        assertEquals(4, actual.get(0).getId());
    }

    @Test
    void updateTest() throws SQLException {
        Connection connection = getConnection();
        Tag beforeUpdate = tagDAO.findById(EXPECTED_TAG.getId());
        EXPECTED_TAG.setName("New name");
        tagDAO.update(EXPECTED_TAG);
        Tag afterUpdate = tagDAO.findById(EXPECTED_TAG.getId());
        assertNotNull(beforeUpdate);
        assertNotNull(afterUpdate);
        assertNotEquals(beforeUpdate.getName(), afterUpdate.getName());
        connection.close();
    }

    @Test
    void deleteTest() {
        tagDAO.save(TEST_TAG);
        Tag beforeDelete = tagDAO.findById(TEST_TAG.getId());
        tagDAO.delete(TEST_TAG);
        Tag afterDelete = tagDAO.findById(TEST_TAG.getId());
        assertNotEquals(beforeDelete, afterDelete);
    }

    @Test
    void deleteByIdTest() {
        tagDAO.deleteById(TEST_ID);
        Tag actual = tagDAO.findById(TEST_TAG.getId());
        assertNull(actual);
    }
}