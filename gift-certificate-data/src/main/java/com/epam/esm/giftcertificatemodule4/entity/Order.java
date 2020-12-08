package com.epam.esm.giftcertificatemodule4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1324820937632935342L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "date")
    private OffsetDateTime date;

    @Column(name = "price")
    private BigDecimal price;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_certificates",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    private Set<GiftCertificate> certificates;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Order(User user) {
        this.user = user;
    }

    public Order(BigDecimal price, User user) {
        this.price = price;
        this.user = user;
    }

    public Order(Long id, OffsetDateTime date, BigDecimal price, User user) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.user = user;
    }

    public Order() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<GiftCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<GiftCertificate> certificates) {
        this.certificates = certificates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                ", user=" + user +
                '}';
    }
}
