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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SqlGiftCertificateDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private GiftCertificateDAO sqlGiftCertificateDAO;

    private static final GiftCertificate EXPECTED_GIFT_CERTIFICATE = new GiftCertificate();
    private static final GiftCertificate NEW_GIFT_CERTIFICATE = new GiftCertificate();
    private static final SearchParametersHolder TEST_SEARCH_PARAMETERS_HOLDER = new SearchParametersHolder();
    private static final long TEST_ID = 1L;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 20;

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_GIFT_CERTIFICATE.setId(TEST_ID);
        EXPECTED_GIFT_CERTIFICATE.setName("Holidays");
        EXPECTED_GIFT_CERTIFICATE.setDescription("Holidays special offer");
        EXPECTED_GIFT_CERTIFICATE.setPrice(64.99);
        EXPECTED_GIFT_CERTIFICATE.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        EXPECTED_GIFT_CERTIFICATE.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        EXPECTED_GIFT_CERTIFICATE.setDuration(1);

        NEW_GIFT_CERTIFICATE.setName("Test");
        NEW_GIFT_CERTIFICATE.setDescription("Test special offer");
        NEW_GIFT_CERTIFICATE.setPrice(99.99);
        NEW_GIFT_CERTIFICATE.setDuration(10);

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
        sqlGiftCertificateDAO.save(NEW_GIFT_CERTIFICATE);
        connection.close();
    }

    @Test
    void updateTest() throws SQLException{
        Connection connection = getConnection();
        sqlGiftCertificateDAO.update(EXPECTED_GIFT_CERTIFICATE);
        connection.close();
    }

    @Test
    void deleteTest() throws SQLException {
        Connection connection = getConnection();
        sqlGiftCertificateDAO.delete(NEW_GIFT_CERTIFICATE);
        connection.close();
    }

    @Test
    void deleteByIdTest() throws SQLException {
        Connection connection = getConnection();
        sqlGiftCertificateDAO.deleteById(TEST_ID);
        connection.close();
    }
}