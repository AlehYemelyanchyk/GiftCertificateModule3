package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule4.entity.Order;
import com.epam.esm.giftcertificatemodule4.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Transactional
    @Override
    public List<Order> findAll(int firstResult, int maxResults) {
        firstResult = Math.max(firstResult, 0);
        maxResults = Math.max(maxResults, 5);
        return orderDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Order findById(Long id) {
        return orderDAO.findById(id);
    }

    @Transactional
    @Override
    public void save(Order object) {
        orderDAO.save(object);
    }

    @Transactional
    @Override
    public void update(Order object) {
        orderDAO.update(object);
    }

    @Transactional
    @Override
    public void delete(Order object) {
        orderDAO.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }
}
