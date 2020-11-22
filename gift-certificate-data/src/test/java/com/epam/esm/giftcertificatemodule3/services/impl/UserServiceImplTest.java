package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.UserDAO;
import com.epam.esm.giftcertificatemodule3.entity.User;
import com.epam.esm.giftcertificatemodule3.services.exceptions.ServiceException;
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
class UserServiceImplTest {

    private User expectedUser = new User();
    private User expectedUser2 = new User();
    private static final Long TEST_ID = 1L;
    private static final Long TEST_ID2 = 2L;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private List<User> expectedList = new ArrayList<>();

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
}