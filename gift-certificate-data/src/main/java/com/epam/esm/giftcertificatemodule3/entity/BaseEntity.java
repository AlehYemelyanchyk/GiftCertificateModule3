package com.epam.esm.giftcertificatemodule3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    private static final long serialVersionUID = 7497478299465838381L;

    @JsonIgnore
    @Column(name = "operation")
    private String operation;

    @JsonIgnore
    @Column(name = "timestamp")
    private Long timestamp;

    public BaseEntity() {
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @PrePersist
    public void onPrePersist() {
        audit("INSERT");
    }

    @PreUpdate
    public void onPreUpdate() {
        audit("UPDATE");
    }

    @PreRemove
    public void onPreRemove() {
        audit("DELETE");
    }

    private void audit(String operation) {
        setOperation(operation);
        setTimestamp((new Date()).getTime());
    }
}
