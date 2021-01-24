package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateRepository;
import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateRepositoryCustom;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private GiftCertificate expectedGiftCertificate = new GiftCertificate();
    private GiftCertificate expectedGiftCertificate2 = new GiftCertificate();
    private SearchParametersHolder testSearchParametersHolder = new SearchParametersHolder();

    private Long id = 1L;
    private static final Long ID = 1L;
    private Tag expectedTag = new Tag();
    private Tag expectedTag2 = new Tag();
    private List<GiftCertificate> expectedList = new ArrayList<>();
    private Page<GiftCertificate> expectedPage;
    private int page = 0;
    private int size = 5;
    private Pageable paging = PageRequest.of(0, 5);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private GiftCertificateRepositoryCustom giftCertificateRepositoryCustom;

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

        expectedPage = new PageImpl<>(expectedList);
    }

    private void setGiftCertificateValues(GiftCertificate giftCertificate) {
        giftCertificate.setId(id++);
        giftCertificate.setName("Test");
        giftCertificate.setDescription("This is a test cert");
        giftCertificate.setPrice(new BigDecimal(9.99));
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setDuration(31);
    }

    @Test
    void findAllListNegativePageTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findAll(paging)).thenReturn(expectedPage);
        List<GiftCertificate> actualList = giftCertificateService.findAll(-1, size);
        assertEquals(expectedPage.getContent(), actualList);
    }

    @Test
    void findAllListNegativeSizeTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findAll(paging)).thenReturn(expectedPage);
        List<GiftCertificate> actualList = giftCertificateService.findAll(page, -1);
        assertEquals(expectedPage.getContent(), actualList);
    }

    @Test
    void findAllReturnNothingTest() throws ServiceException {
        List<GiftCertificate> emptyList = new ArrayList<>();
        Mockito.when(giftCertificateRepository.findAll(paging)).thenReturn(new PageImpl<>(emptyList));
        List<GiftCertificate> actualList = giftCertificateService.findAll(page, -1);
        assertEquals(emptyList, actualList);
    }

    @Test
    void findAllListReturnTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findAll(paging)).thenReturn(expectedPage);
        List<GiftCertificate> actualList = giftCertificateService.findAll(page, size);
        assertEquals(expectedPage.getContent(), actualList);
    }

    @Test
    void findAllTagsReturnTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findAll(paging)).thenReturn(expectedPage);
        List<GiftCertificate> actualList = giftCertificateService.findAll(page, size);
        for (int i = 0; i < expectedPage.getContent().size(); i++) {
            assertEquals(expectedPage.getContent().get(i).getTags(), actualList.get(i).getTags());
        }
    }

    @Test
    void findByIdReturnTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findById(ID)).thenReturn(Optional.of(expectedGiftCertificate));
        GiftCertificate actualGiftCertificate = giftCertificateService.findById(ID);
        assertEquals(expectedGiftCertificate, actualGiftCertificate);
    }

    @Test
    void findByIdReturnNullTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findById(ID)).thenReturn(Optional.empty());
        GiftCertificate actualGiftCertificate = giftCertificateService.findById(ID);
        assertNull(actualGiftCertificate);
    }

    @Test
    void findByIdTagsReturnTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(expectedGiftCertificate));
        GiftCertificate actualCertificate = giftCertificateService.findById(id);
        assertEquals(expectedGiftCertificate.getTags(), actualCertificate.getTags());
    }

    @Test
    void findByListReturnTest() throws ServiceException {
        Mockito.when(giftCertificateRepositoryCustom.findBy(testSearchParametersHolder, paging)).thenReturn(expectedList);
        List<GiftCertificate> actualList = giftCertificateService.findBy(testSearchParametersHolder, page, size);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByTagsReturnTest() throws ServiceException {
        Mockito.when(giftCertificateRepositoryCustom.findBy(testSearchParametersHolder, paging)).thenReturn(expectedList);
        List<GiftCertificate> actualList = giftCertificateService.findBy(testSearchParametersHolder, page, size);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getTags(), actualList.get(i).getTags());
        }
    }

    @Test
    void saveInvocationTest() throws ServiceException {
        giftCertificateService.save(expectedGiftCertificate);
        Mockito.verify(giftCertificateRepository).save(expectedGiftCertificate);
    }

    @Test
    void updateInvocationTest() throws ServiceException {
        Mockito.when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(expectedGiftCertificate));
        expectedGiftCertificate.setId(id);
        giftCertificateService.update(expectedGiftCertificate);
        Mockito.verify(giftCertificateRepository).save(expectedGiftCertificate);
    }

    @Test
    void updateNoInvocationTest() throws ServiceException {
        giftCertificateService.update(expectedGiftCertificate);
        Mockito.verify(giftCertificateRepository, Mockito.never()).save(expectedGiftCertificate);
    }

    @Test
    void deleteInvocationTest() throws ServiceException {
        giftCertificateService.delete(expectedGiftCertificate);
        Mockito.verify(giftCertificateRepository).delete(expectedGiftCertificate);
    }

    @Test
    void deleteByIdInvocationTest() {
        giftCertificateService.deleteById(id);
        Mockito.verify(giftCertificateRepository).deleteById(id);
    }
}