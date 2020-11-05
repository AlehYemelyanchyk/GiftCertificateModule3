package com.epam.esm.giftcertificatemodule3.model;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaggedGiftCertificate extends GiftCertificate {
    private static final Long serialVersionUID = 1724820758632935338L;

    private List<Tag> tags = new ArrayList<>();

    public TaggedGiftCertificate() {
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}