package com.epam.esm.giftcertificatemodule4.services.impl;

import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateRepository;
import com.epam.esm.giftcertificatemodule4.dao.GiftCertificateRepositoryCustom;
import com.epam.esm.giftcertificatemodule4.dao.TagRepository;
import com.epam.esm.giftcertificatemodule4.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule4.entity.Tag;
import com.epam.esm.giftcertificatemodule4.model.SearchParametersHolder;
import com.epam.esm.giftcertificatemodule4.services.GiftCertificateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateRepositoryCustom giftCertificateRepositoryCustom;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      GiftCertificateRepositoryCustom giftCertificateRepositoryCustom,
                                      TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.giftCertificateRepositoryCustom = giftCertificateRepositoryCustom;
        this.tagRepository = tagRepository;
    }


    @Transactional
    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 5);
        Pageable paging = PageRequest.of(page, size);
        Page<GiftCertificate> all = giftCertificateRepository.findAll(paging);
        return all.getContent();
    }

    @Transactional
    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public List<GiftCertificate> findBy(SearchParametersHolder searchParametersHolder,
                                        int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 5);
        Pageable paging = PageRequest.of(page, size);
        return giftCertificateRepositoryCustom.findBy(searchParametersHolder, paging);
    }

    @Transactional
    @Override
    public void save(GiftCertificate giftCertificate) {
        getTagsId(giftCertificate);
        giftCertificate.setCreateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
        giftCertificateRepository.save(giftCertificate);
    }

    @Transactional
    @Override
    public void update(GiftCertificate giftCertificate) {
        getTagsId(giftCertificate);
        GiftCertificate persistedGiftCertificate = findById(giftCertificate.getId());
        setUpdatedFields(giftCertificate, persistedGiftCertificate);
        giftCertificateRepository.save(giftCertificate);
    }

    @Transactional
    @Override
    public void delete(GiftCertificate giftCertificate) {
        giftCertificateRepository.delete(giftCertificate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        giftCertificateRepository.deleteById(id);
    }

    private void getTagsId(GiftCertificate giftCertificate) {
        Set<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            tags.forEach(tag -> {
                Tag savedTag = tagRepository.findByName(tag.getName());
                if (savedTag == null) {
                    savedTag = tagRepository.save(tag);
                }
                if (savedTag != null) {
                    tag.setId(savedTag.getId());
                }
            });
        }
    }

    private void setUpdatedFields(GiftCertificate giftCertificate, GiftCertificate persistedGiftCertificate) {
        if (giftCertificate.getTags() == null) {
            giftCertificate.setTags(persistedGiftCertificate.getTags());
        }
        if (giftCertificate.getName() == null) {
            giftCertificate.setName(persistedGiftCertificate.getName());
        }
        if (giftCertificate.getDescription() == null) {
            giftCertificate.setDescription(persistedGiftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() == null) {
            giftCertificate.setPrice(persistedGiftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() == null) {
            giftCertificate.setDuration(persistedGiftCertificate.getDuration());
        }
        giftCertificate.setCreateDate(persistedGiftCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now().toOffsetDateTime());
    }
}
