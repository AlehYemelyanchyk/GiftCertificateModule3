package com.epam.esm.giftcertificatemodule3.impl;

import com.epam.esm.giftcertificatemodule3.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
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
class SqlGiftCertificateDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    GiftCertificateDAO sqlGiftCertificateDAO;

    private static final GiftCertificate EXPECTED_GIFT_CERTIFICATE = new GiftCertificate();
    private static final GiftCertificate TEST_GIFT_CERTIFICATE = new GiftCertificate();
    private static final SearchParametersHolder TEST_SEARCH_PARAMETERS_HOLDER = new SearchParametersHolder();
    private static final long TEST_ID = 1L;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_GIFT_CERTIFICATE.setId(TEST_ID);
        EXPECTED_GIFT_CERTIFICATE.setName("Holidays");
        EXPECTED_GIFT_CERTIFICATE.setDescription("Holidays special offer");
        EXPECTED_GIFT_CERTIFICATE.setPrice(64.99);
        EXPECTED_GIFT_CERTIFICATE.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        EXPECTED_GIFT_CERTIFICATE.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        EXPECTED_GIFT_CERTIFICATE.setDuration(1);

        TEST_GIFT_CERTIFICATE.setId(11L);
        TEST_GIFT_CERTIFICATE.setName("Test");
        TEST_GIFT_CERTIFICATE.setDescription("Test special offer");
        TEST_GIFT_CERTIFICATE.setPrice(99.99);
        TEST_GIFT_CERTIFICATE.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        TEST_GIFT_CERTIFICATE.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        TEST_GIFT_CERTIFICATE.setDuration(0);

        TEST_SEARCH_PARAMETERS_HOLDER.setTagName("red");
        TEST_SEARCH_PARAMETERS_HOLDER.setName("ABC");
        TEST_SEARCH_PARAMETERS_HOLDER.setDescription("New Year gift certificate");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortBy("name");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortOrder("desc");
        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void findAllTest() {
        List<GiftCertificate> actualList = sqlGiftCertificateDAO.findAll(FIRST_RESULT, MAX_RESULTS);
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        GiftCertificate actual = sqlGiftCertificateDAO.findById(TEST_ID);
        assertEquals(EXPECTED_GIFT_CERTIFICATE.getId(), actual.getId());
    }

    @Test
    void saveTest() throws SQLException{
        Connection connection = getConnection();
        List<GiftCertificate> listBefore = sqlGiftCertificateDAO.findAll(FIRST_RESULT, 12);
        sqlGiftCertificateDAO.save(TEST_GIFT_CERTIFICATE);
        List<GiftCertificate> listAfter = sqlGiftCertificateDAO.findAll(FIRST_RESULT, 12);
        assertNotNull(listBefore);
        assertNotNull(listAfter);
        assertNotEquals(listBefore, listAfter);
        connection.close();
    }

    @Test
    void updateTest() throws SQLException{
        Connection connection = getConnection();
        GiftCertificate beforeUpdate = sqlGiftCertificateDAO.findById(EXPECTED_GIFT_CERTIFICATE.getId());
        EXPECTED_GIFT_CERTIFICATE.setName("New name");
        sqlGiftCertificateDAO.update(EXPECTED_GIFT_CERTIFICATE);
        GiftCertificate afterUpdate = sqlGiftCertificateDAO.findById(EXPECTED_GIFT_CERTIFICATE.getId());
        assertNotNull(beforeUpdate);
        assertNotNull(afterUpdate);
        assertNotEquals(beforeUpdate.getName(), afterUpdate.getName());
        connection.close();
    }

    @Test
    void deleteTest() {
        sqlGiftCertificateDAO.save(TEST_GIFT_CERTIFICATE);
        GiftCertificate beforeDelete = sqlGiftCertificateDAO.findById(TEST_GIFT_CERTIFICATE.getId());
        sqlGiftCertificateDAO.delete(TEST_GIFT_CERTIFICATE);
        GiftCertificate afterDelete = sqlGiftCertificateDAO.findById(TEST_GIFT_CERTIFICATE.getId());
        assertNotEquals(beforeDelete, afterDelete);
    }

    @Test
    void deleteByIdTest() {
        sqlGiftCertificateDAO.deleteById(TEST_ID);
        GiftCertificate actual = sqlGiftCertificateDAO.findById(EXPECTED_GIFT_CERTIFICATE.getId());
        assertNull(actual);
    }
}