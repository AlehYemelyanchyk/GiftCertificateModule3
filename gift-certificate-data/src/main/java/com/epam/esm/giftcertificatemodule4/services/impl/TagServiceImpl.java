package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.TagRepository;
import com.epam.esm.giftcertificatemodule4.dao.TagRepositoryCustom;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.services.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagRepositoryCustom tagRepositoryCustom;

    public TagServiceImpl(TagRepository tagRepository, TagRepositoryCustom tagRepositoryCustom) {
        this.tagRepository = tagRepository;
        this.tagRepositoryCustom = tagRepositoryCustom;
    }

    @Transactional
    @Override
    public List<Tag> findAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 5);
        Pageable paging = PageRequest.of(page, size);
        return tagRepository.findAll(paging).getContent();
    }

    @Transactional
    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Tag> findMostPopularTags(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);
        Pageable paging = PageRequest.of(page, size);
        return tagRepositoryCustom.findMostPopularTags(paging);
    }

    @Transactional
    @Override
    public void save(Tag object) {
        tagRepository.save(object);
    }

    @Transactional
    @Override
    public void update(Tag object) {
        tagRepository.save(object);
    }

    @Transactional
    @Override
    public void delete(Tag object) {
        tagRepository.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
