package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserRepository;
import com.epam.esm.giftcertificatemodule4.dao.UserRepositoryCustom;
import com.epam.esm.giftcertificatemodule4.entity.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User expectedUser = new User();
    private User expectedUser2 = new User();
    private static final Long TEST_ID = 1L;
    private static final int PAGE = 0;
    private static final int SIZE = 5;
    private List<User> expectedList = new ArrayList<>();
    private Page<User> expectedPage;
    private Exception expectedException = new UnsupportedOperationException();
    private Pageable paging = PageRequest.of(0, 5);

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRepositoryCustom userRepositoryCustom;

    @BeforeEach
    public void init() {
        expectedUser.setName("TestUser1");
        expectedUser2.setName("TestUser2");

        expectedList.add(expectedUser);
        expectedList.add(expectedUser2);

        expectedPage = new PageImpl<>(expectedList);
    }

    @Test
    void findAllListReturnTest() throws ServiceException {
        Mockito.when(userRepository.findAll(paging)).thenReturn(expectedPage);
        List<User> actualList = userService.findAll(PAGE, SIZE);
        assertEquals(expectedPage.getContent(), actualList);
    }

    @Test
    void findByIdReturnTest() throws ServiceException {
        Mockito.when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findById(TEST_ID);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByStringIdReturnTest() throws ServiceException {
        Mockito.when(userRepositoryCustom.findById("TEST_ID")).thenReturn(expectedUser);
        User actualUser = userService.findById("TEST_ID");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void saveInvocationTest() {
        userService.save(expectedUser);
        Mockito.verify(userRepository).save(expectedUser);
    }

    @Test
    void updateExceptionTest() {
        Exception actual = null;
        try {
            userService.update(expectedUser);
        } catch (Exception e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(expectedException.getClass(), actual.getClass());
    }

    @Test
    void deleteExceptionTest() {
        Exception actual = null;
        try {
            userService.delete(expectedUser);
        } catch (Exception e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(expectedException.getClass(), actual.getClass());
    }

    @Test
    void deleteByIdExceptionTest() {
        Exception actual = null;
        try {
            userService.deleteById(TEST_ID);
        } catch (Exception e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(expectedException.getClass(), actual.getClass());
    }
}