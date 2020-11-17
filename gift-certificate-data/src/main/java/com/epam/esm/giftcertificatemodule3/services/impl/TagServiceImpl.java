package com.epam.esm.giftcertificatemodule3.services.impl;

import com.epam.esm.giftcertificatemodule3.dao.OrderDAO;
import com.epam.esm.giftcertificatemodule3.dao.TagDAO;
import com.epam.esm.giftcertificatemodule3.entity.Order;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import com.epam.esm.giftcertificatemodule3.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule3.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final OrderDAO orderDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, OrderDAO orderDAO) {
        this.tagDAO = tagDAO;
        this.orderDAO = orderDAO;
    }

    @Transactional
    @Override
    public List<Tag> findAll(int firstResult, int maxResults) {
        return tagDAO.findAll(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Tag findById(Integer id) {
        return tagDAO.findById(id);
    }

    @Transactional
    @Override
    public Tag findByName(String name) {
        return tagDAO.findByName(name);
    }

    @Transactional
    @Override
    public List<Tag> findByHighestUserExpense(SearchParametersHolder searchParametersHolder) {
        List<Order> orders = orderDAO.findByHighestUserExpense(searchParametersHolder);
        double maxSpend = orders.stream()
                .mapToDouble(Order::getPrice)
                .max()
                .getAsDouble();
        Set<Map.Entry<Tag, Long>> entries = orders.stream()
                .filter(order -> order.getPrice() == maxSpend)
                .flatMap(order -> order.getUser().getOrders().stream())
                .flatMap(order -> order.getCertificates().stream())
                .flatMap(giftCertificate -> giftCertificate.getTags().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet();
        Long maxAmount = entries.stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .get().getValue();
        List<Tag> tags = entries.stream()
                .filter(e -> e.getValue().equals(maxAmount))
                .map(e -> e.getKey())
                .collect(Collectors.toList());
        return tags;
    }

    @Transactional
    @Override
    public void save(Tag object) {
        tagDAO.save(object);
    }

    @Transactional
    @Override
    public void update(Tag object) {
        tagDAO.update(object);
    }

    @Transactional
    @Override
    public void delete(Tag object) {
        tagDAO.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        tagDAO.deleteById(id);
    }
}
