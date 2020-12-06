package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule4.entity.Order;
import com.epam.esm.giftcertificatemodule4.entity.User;
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
class OrderServiceImplTest {

    private Order expectedOrder = new Order();
    private Order expectedOrder2 = new Order();
    private User testUser = new User();
    private static final Long TEST_ID = 1L;
    private static final Long TEST_ID2 = 2L;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 5;
    private List<Order> expectedList = new ArrayList<>();

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderDAO orderDAO;

    @BeforeEach
    public void init() {
        expectedOrder.setUser(testUser);
        expectedOrder.setPrice(9.99);
        expectedOrder.setDate(ZonedDateTime.now().toOffsetDateTime());
        expectedOrder.setId(TEST_ID);

        expectedOrder2.setUser(testUser);
        expectedOrder2.setPrice(8.99);
        expectedOrder2.setDate(ZonedDateTime.now().toOffsetDateTime());
        expectedOrder2.setId(TEST_ID2);

        expectedList.add(expectedOrder);
        expectedList.add(expectedOrder2);
    }

    @Test
    void findAllListReturnTest() throws ServiceException {
        Mockito.when(orderDAO.findAll(FIRST_RESULT, MAX_RESULTS)).thenReturn(expectedList);
        List<Order> actualList = orderService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertEquals(expectedList, actualList);
    }

    @Test
    void findByIdReturnTest() throws ServiceException {
        Mockito.when(orderDAO.findById(TEST_ID)).thenReturn(expectedOrder);
        Order actualOrder = orderService.findById(TEST_ID);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void saveInvocationTest() throws ServiceException {
        orderService.save(expectedOrder);
        Mockito.verify(orderDAO).save(expectedOrder);
    }

    @Test
    void updateInvocationTest() throws ServiceException {
        orderService.update(expectedOrder);
        Mockito.verify(orderDAO).update(expectedOrder);
    }

    @Test
    void deleteInvocationTest() throws ServiceException {
        orderService.delete(expectedOrder);
        Mockito.verify(orderDAO).delete(expectedOrder);
    }

    @Test
    void deleteByIdInvocationTest() {
        orderService.deleteById(TEST_ID);
        Mockito.verify(orderDAO).deleteById(TEST_ID);
    }
}