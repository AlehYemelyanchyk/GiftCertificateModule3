package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.UserDAO;
import com.epam.esm.giftcertificatemodule4.entity.User;
import com.epam.esm.giftcertificatemodule4.services.exceptions.ServiceException;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User expectedUser = new User();
    private User expectedUser2 = new User();
    private static final Long TEST_ID = 1L;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private List<User> expectedList = new ArrayList<>();
    private Exception expectedException = new UnsupportedOperationException();

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    @BeforeEach
    public void init() {
        expectedUser.setName("TestUser1");
        expectedUser2.setName("TestUser2");

        expectedList.add(expectedUser);
        expectedList.add(expectedUser2);
    }

    @Test
    void findAllListReturnTest() throws ServiceException {
        Mockito.when(userDAO.findAll(FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<User> actualList = userService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByIdReturnTest() throws ServiceException {
        Mockito.when(userDAO.findById(TEST_ID)).thenReturn(expectedUser);
        User actualUser = userService.findById(TEST_ID);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void saveExceptionTest() {
        Exception actual = null;
        try {
            userService.save(expectedUser);
        } catch (Exception e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(expectedException.getClass(), actual.getClass());
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