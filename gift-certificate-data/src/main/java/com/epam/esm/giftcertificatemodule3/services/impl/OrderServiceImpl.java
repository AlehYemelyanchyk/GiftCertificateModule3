package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule3.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Transactional
    @Override
    public List<Order> findAll(int firstResult, int maxResults) {
        return orderDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Order findById(Long id) {
        return orderDAO.findById(id);
    }

    @Transactional
    @Override
    public List<Order> findHighestSpendByUser(SearchParametersHolder searchParametersHolder) {
        return orderDAO.findHighestSpendByUser(searchParametersHolder);
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
