package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.TagRepository;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private Tag expectedTag = new Tag();
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "blue";
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private Pageable paging = PageRequest.of(0, 5);

    @BeforeEach
    public void init() {
        expectedTag.setName(TEST_NAME);
    }

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Test
    void findAllTest() {
        List<Tag> expectedList = new ArrayList<>();
        expectedList.add(expectedTag);
        Page<Tag> expectedPage = new PageImpl<>(expectedList);

        Mockito.when(tagRepository.findAll(paging)).thenReturn(expectedPage);
        List<Tag> actualList = tagService.findAll(PAGE, SIZE);
        assertEquals(expectedPage.getContent(), actualList);
    }

    @Test
    void findByIdTest() {
        Mockito.when(tagRepository.findById(TEST_ID)).thenReturn(Optional.of(expectedTag));
        Tag actualTag = tagService.findById(TEST_ID);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void findByNameTest() {
        Mockito.when(tagRepository.findByName(TEST_NAME)).thenReturn(expectedTag);
        Tag actualTag = tagService.findByName(TEST_NAME);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void findByHighestUserExpense() {
    }

    @Test
    void saveInvocationTest() {
        tagService.save(expectedTag);
        Mockito.verify(tagRepository).save(expectedTag);
    }

    @Test
    void updateInvocationTest() {
        tagService.update(expectedTag);
        Mockito.verify(tagRepository).save(expectedTag);
    }

    @Test
    void deleteInvocationTest() {
        tagService.delete(expectedTag);
        Mockito.verify(tagRepository).delete(expectedTag);
    }

    @Test
    void deleteByIdInvocationTest() {
        tagService.deleteById(TEST_ID);
        Mockito.verify(tagRepository).deleteById(TEST_ID);
    }
}