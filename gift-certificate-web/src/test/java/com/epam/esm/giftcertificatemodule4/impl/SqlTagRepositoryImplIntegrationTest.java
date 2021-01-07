package com.epam.esm.giftcertificatemodule4.impl;

import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.services.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class SqlTagRepositoryImplIntegrationTest extends AbstractIntegrationTest {

    private Connection connection;

    @Autowired
    TagService tagService;

    private static final Tag EXPECTED_TAG = new Tag();
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_TAG.setName("tag1");
        EXPECTED_TAG.setId(1L);
        connection = executeSqlStartScript();
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlEndScript(connection);
    }

    @Test
    void saveTest() {
        List<Tag> beforeTest = tagService.findAll(FIRST_RESULT, MAX_RESULTS);
        Tag newTag = new Tag();
        newTag.setName("NewTag");
        tagService.save(newTag);
        List<Tag> afterTest = tagService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(beforeTest);
        assertNotNull(afterTest);
        assertEquals(beforeTest.size(), afterTest.size());
    }

    @Test
    void findAllTest() {
        List<Tag> actualList = tagService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        Tag actual = tagService.findById(EXPECTED_TAG.getId());
        assertEquals(EXPECTED_TAG.getId(), actual.getId());
    }

    @Test
    void findByNameTest() {
        Tag actual = tagService.findByName(EXPECTED_TAG.getName());
        assertEquals(EXPECTED_TAG.getId(), actual.getId());
    }

    @Test
    void findMostPopularTagsTest() {
        List<Tag> actual = tagService.findMostPopularTags(FIRST_RESULT, MAX_RESULTS);
        assertEquals(4, actual.get(0).getId());
    }

    @Test
    void updateTest() {
        Tag beforeUpdate = tagService.findById(EXPECTED_TAG.getId());
        EXPECTED_TAG.setName("New name");
        tagService.update(EXPECTED_TAG);
        Tag afterUpdate = tagService.findById(EXPECTED_TAG.getId());
        assertNotNull(beforeUpdate);
        assertNotNull(afterUpdate);
        assertNotEquals(beforeUpdate.getName(), afterUpdate.getName());
    }

    @Test
    void deleteTest() {
       // Method not used
    }

    @Test
    void deleteByIdTest() {
        tagService.deleteById(EXPECTED_TAG.getId());
        Tag actual = tagService.findById(EXPECTED_TAG.getId());
        assertNull(actual);
    }
}