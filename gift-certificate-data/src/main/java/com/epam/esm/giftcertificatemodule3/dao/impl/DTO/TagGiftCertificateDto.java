package com.epam.esm.giftcertificatemodule3.dao.impl.DTO;

import com.epam.esm.giftcertificatemodule3.entity.GiftCertificate;
import com.epam.esm.giftcertificatemodule3.entity.Tag;

public class TagGiftCertificateDto {

   private Tag tag;
   private GiftCertificate giftCertificate;

    public TagGiftCertificateDto() {
    }

    public TagGiftCertificateDto(Tag tag, GiftCertificate giftCertificate) {
        this.tag = tag;
        this.giftCertificate = giftCertificate;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }
}
