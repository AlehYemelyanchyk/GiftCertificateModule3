package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.OrderRepository;
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
    private Page<Order> expectedPage;
    private Pageable paging = PageRequest.of(0, 5);

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        expectedOrder.setUser(testUser);
        expectedOrder.setPrice(new BigDecimal(9.99));
        expectedOrder.setDate(ZonedDateTime.now().toOffsetDateTime());
        expectedOrder.setId(TEST_ID);

        expectedOrder2.setUser(testUser);
        expectedOrder2.setPrice(new BigDecimal(8.99));
        expectedOrder2.setDate(ZonedDateTime.now().toOffsetDateTime());
        expectedOrder2.setId(TEST_ID2);

        expectedList.add(expectedOrder);
        expectedList.add(expectedOrder2);

        expectedPage = new PageImpl<>(expectedList);
    }

    @Test
    void findAllListReturnTest() throws ServiceException {
        Mockito.when(orderRepository.findAll(paging)).thenReturn(expectedPage);
        List<Order> actualList = orderService.findAll(FIRST_RESULT, MAX_RESULTS);
        assertEquals(expectedPage.getContent(), actualList);
    }

    @Test
    void findByIdReturnTest() throws ServiceException {
        Mockito.when(orderRepository.findById(TEST_ID)).thenReturn(Optional.of(expectedOrder));
        Order actualOrder = orderService.findById(TEST_ID);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void saveInvocationTest() throws ServiceException {
        orderService.save(expectedOrder);
        Mockito.verify(orderRepository).save(expectedOrder);
    }

    @Test
    void updateInvocationTest() throws ServiceException {
        orderService.update(expectedOrder);
        Mockito.verify(orderRepository).save(expectedOrder);
    }

    @Test
    void deleteInvocationTest() throws ServiceException {
        orderService.delete(expectedOrder);
        Mockito.verify(orderRepository).delete(expectedOrder);
    }

    @Test
    void deleteByIdInvocationTest() {
        orderService.deleteById(TEST_ID);
        Mockito.verify(orderRepository).deleteById(TEST_ID);
    }
}