package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private Tag expectedTag = new Tag();
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "blue";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;

    @BeforeEach
    public void init() {
        expectedTag.setName("blue");
    }

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;

    @Test
    void findAllTest() {
        List<Tag> expectedList = new ArrayList<>();
        expectedList.add(expectedTag);

        Mockito.when(tagDAO.findAll(FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<Tag> actualList = tagService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByIdTest() {
        Mockito.when(tagDAO.findById(TEST_ID)).thenReturn(expectedTag);
        Tag actualTag = tagService.findById(TEST_ID);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void findByNameTest() {
        Mockito.when(tagDAO.findByName(TEST_NAME)).thenReturn(expectedTag);
        Tag actualTag = tagService.findByName(TEST_NAME);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void findByHighestUserExpense() {
    }

    @Test
    void saveInvocationTest() {
        tagService.save(expectedTag);
        Mockito.verify(tagDAO).save(expectedTag);
    }

    @Test
    void updateInvocationTest() {
        tagService.update(expectedTag);
        Mockito.verify(tagDAO).update(expectedTag);
    }

    @Test
    void deleteInvocationTest() {
        tagService.delete(expectedTag);
        Mockito.verify(tagDAO).delete(expectedTag);
    }

    @Test
    void deleteByIdInvocationTest() {
        tagService.deleteById(TEST_ID);
        Mockito.verify(tagDAO).deleteById(TEST_ID);
    }
}