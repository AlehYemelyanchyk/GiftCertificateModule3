package com.epam.esm.giftcertificatemodule4.impl;

import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule4.services.GiftCertificateService;
import com.epam.esm.giftcertificatemodule4.services.TagService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class SqlGiftCertificateRepositoryImplIntegrationTest extends AbstractIntegrationTest {

    private Connection connection;

    @Autowired
    private GiftCertificateService sqlGiftCertificateService;

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private TagService tagService;

    private static final GiftCertificate EXPECTED_GIFT_CERTIFICATE = new GiftCertificate();
    private static final GiftCertificate NEW_GIFT_CERTIFICATE = new GiftCertificate();
    private static final SearchParametersHolder TEST_SEARCH_PARAMETERS_HOLDER = new SearchParametersHolder();
    private static final int PAGE = 0;
    private static final int SIZE = 20;

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_GIFT_CERTIFICATE.setId(1L);
        EXPECTED_GIFT_CERTIFICATE.setName("Holidays");
        EXPECTED_GIFT_CERTIFICATE.setDescription("Holidays special offer");
        EXPECTED_GIFT_CERTIFICATE.setPrice(new BigDecimal(64.99));
        EXPECTED_GIFT_CERTIFICATE.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        EXPECTED_GIFT_CERTIFICATE.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        EXPECTED_GIFT_CERTIFICATE.setDuration(1);

        NEW_GIFT_CERTIFICATE.setName("Test");
        NEW_GIFT_CERTIFICATE.setDescription("Test special offer");
        NEW_GIFT_CERTIFICATE.setPrice(new BigDecimal(99.99));
        NEW_GIFT_CERTIFICATE.setDuration(10);

        TEST_SEARCH_PARAMETERS_HOLDER.setTagName("red");
        TEST_SEARCH_PARAMETERS_HOLDER.setName("ABC");
        TEST_SEARCH_PARAMETERS_HOLDER.setDescription("New Year gift certificate");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortBy("name");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortOrder("desc");
        connection = executeSqlStartScript();
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlEndScript(connection);
    }

    @Test
    void findAllTest() {
        List<GiftCertificate> actualList = sqlGiftCertificateService.findAll(PAGE, SIZE);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        GiftCertificate actual = sqlGiftCertificateService.findById(1L);
        assertNotNull(actual);
        assertEquals(EXPECTED_GIFT_CERTIFICATE.getId(), actual.getId());
    }

    @Test
    void saveNoTagsTest() {
        List<GiftCertificate> beforeTest = giftCertificateService.findAll(PAGE, SIZE);
        giftCertificateService.save(NEW_GIFT_CERTIFICATE);
        List<GiftCertificate> afterTest = giftCertificateService.findAll(PAGE, SIZE);
        assertNotNull(beforeTest);
        assertNotNull(afterTest);
        assertEquals(beforeTest.size() + 1, afterTest.size());
    }

    @Test
    void saveNewTagsTest() {
        Set<Tag> tags = new HashSet<>();
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setName("new tag 1");
        tag2.setName("new tag 2");
        tags.add(tag1);
        tags.add(tag2);
        NEW_GIFT_CERTIFICATE.setTags(tags);
        List<Tag> tagsBeforeTest = tagService.findAll(PAGE, SIZE);

        giftCertificateService.save(NEW_GIFT_CERTIFICATE);

        List<GiftCertificate> afterTest = giftCertificateService.findAll(PAGE, SIZE);
        List<Tag> tagsAfterTest = tagService.findAll(PAGE, SIZE);
        assertNotNull(afterTest);
        assertNotNull(tagsBeforeTest);
        assertNotNull(tagsAfterTest);
        GiftCertificate actualCertificate = afterTest.get(afterTest.size() - 1);
        assertEquals(NEW_GIFT_CERTIFICATE, actualCertificate);
        assertEquals(tagsBeforeTest.size() + 2, tagsAfterTest.size());
    }

    @Test
    void saveNewAndExistingTagsTest() {
        Set<Tag> tags = new HashSet<>();
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setId(1L);
        tag1.setName("tag1");
        tag2.setName("new tag 2");
        tags.add(tag1);
        tags.add(tag2);
        NEW_GIFT_CERTIFICATE.setTags(tags);
        List<Tag> tagsBeforeTest = tagService.findAll(PAGE, SIZE);

        giftCertificateService.save(NEW_GIFT_CERTIFICATE);

        List<GiftCertificate> afterTest = giftCertificateService.findAll(PAGE, SIZE);
        List<Tag> tagsAfterTest = tagService.findAll(PAGE, SIZE);
        assertNotNull(afterTest);
        assertNotNull(tagsBeforeTest);
        assertNotNull(tagsAfterTest);
        GiftCertificate actualCertificate = afterTest.get(afterTest.size() - 1);
        assertEquals(NEW_GIFT_CERTIFICATE, actualCertificate);
        assertEquals(tagsBeforeTest.size() + 1, tagsAfterTest.size());
    }

    @Test
    void updateTest() {
        GiftCertificate beforeTest = giftCertificateService.findById(EXPECTED_GIFT_CERTIFICATE.getId());
        String newName = "New Name";
        EXPECTED_GIFT_CERTIFICATE.setName(newName);
        giftCertificateService.update(EXPECTED_GIFT_CERTIFICATE);
        GiftCertificate afterTest = giftCertificateService.findById(EXPECTED_GIFT_CERTIFICATE.getId());
        assertNotNull(beforeTest);
        assertNotNull(afterTest);
        assertEquals(newName, afterTest.getName());
    }

    @Test
    void deleteTest() {
//        not used method;
    }

    @Test
    void deleteByIdTest() {
        List<GiftCertificate> beforeTest = giftCertificateService.findAll(PAGE, SIZE);
        giftCertificateService.deleteById(1L);
        List<GiftCertificate> afterTest = giftCertificateService.findAll(PAGE, SIZE);
        assertNotNull(beforeTest);
        assertNotNull(afterTest);
        assertEquals(beforeTest.size() - 1, afterTest.size());
    }
}