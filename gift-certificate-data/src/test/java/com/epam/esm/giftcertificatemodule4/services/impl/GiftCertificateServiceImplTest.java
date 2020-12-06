package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateDAO;
import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule4.services.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private GiftCertificate expectedGiftCertificate = new GiftCertificate();
    private GiftCertificate expectedGiftCertificate2 = new GiftCertificate();
    private SearchParametersHolder testSearchParametersHolder = new SearchParametersHolder();

    private Long id = 1L;
    private Tag expectedTag = new Tag();
    private Tag expectedTag2 = new Tag();
    private List<GiftCertificate> expectedList = new ArrayList<>();
    private static final long TEST_ID = 1L;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    @BeforeEach
    public void init() {
        expectedTag.setName("blue");
        expectedTag2.setName("red");
        setGiftCertificateValues(expectedGiftCertificate);
        setGiftCertificateValues(expectedGiftCertificate2);

        expectedList.add(expectedGiftCertificate);
        expectedList.add(expectedGiftCertificate2);

        testSearchParametersHolder.setTagName("Test tag name");
        testSearchParametersHolder.setName("Test name");
        testSearchParametersHolder.setDescription("Test description");
        testSearchParametersHolder.setSortBy("name");
        testSearchParametersHolder.setSortOrder("desc");
    }

    private void setGiftCertificateValues(GiftCertificate giftCertificate) {
        giftCertificate.setId(id++);
        giftCertificate.setName("Test");
        giftCertificate.setDescription("This is a test cert");
        giftCertificate.setPrice(9.99);
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setDuration(31);
    }

    @Test
    void findAllListReturnTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findAll(FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<GiftCertificate> actualList = giftCertificateService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAllTagsReturnTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findAll(FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<GiftCertificate> actualList = giftCertificateService.findAll(FIRST_RESULT, MAX_RESULTS);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getTags(), actualList.get(i).getTags());
        }
    }

    @Test
    void findByIdReturnTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findById(TEST_ID)).thenReturn(expectedGiftCertificate);
        GiftCertificate actualGiftCertificate = giftCertificateService.findById(TEST_ID);
        assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void findByIdTagsReturnTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findById(TEST_ID)).thenReturn(expectedGiftCertificate);
        GiftCertificate actualCertificate = giftCertificateService.findById(TEST_ID);
        assertEquals(expectedGiftCertificate.getTags(), actualCertificate.getTags());
    }

    @Test
    void findByListReturnTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findBy(testSearchParametersHolder, FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<GiftCertificate> actualList = giftCertificateService.findBy(testSearchParametersHolder, FIRST_RESULT, MAX_RESULTS);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByTagsReturnTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findBy(testSearchParametersHolder, FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<GiftCertificate> actualList = giftCertificateService.findBy(testSearchParametersHolder, FIRST_RESULT, MAX_RESULTS);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getTags(), actualList.get(i).getTags());
        }
    }

    @Test
    void saveInvocationTest() throws ServiceException {
        giftCertificateService.save(expectedGiftCertificate);
        Mockito.verify(giftCertificateDAO).save(expectedGiftCertificate);
    }

    @Test
    void updateInvocationTest() throws ServiceException {
        giftCertificateService.update(expectedGiftCertificate);
        Mockito.verify(giftCertificateDAO).update(expectedGiftCertificate);
    }

    @Test
    void deleteInvocationTest() throws ServiceException {
        giftCertificateService.delete(expectedGiftCertificate);
        Mockito.verify(giftCertificateDAO).delete(expectedGiftCertificate);
    }

    @Test
    void deleteByIdInvocationTest() {
        giftCertificateService.deleteById(TEST_ID);
        Mockito.verify(giftCertificateDAO).deleteById(TEST_ID);
    }
}