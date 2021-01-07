package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.OrderRepository;
import com.epam.esm.giftcertificatemodule4.entity.Order;
import com.epam.esm.giftcertificatemodule4.services.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public List<Order> findAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 5);
        Pageable paging = PageRequest.of(page, size);
        return orderRepository.findAll(paging).getContent();
    }

    @Transactional
    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void save(Order object) {
        orderRepository.save(object);
    }

    @Transactional
    @Override
    public void update(Order object) {
        orderRepository.save(object);
    }

    @Transactional
    @Override
    public void delete(Order object) {
        orderRepository.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
