package com.epam.esm.giftcertificatemodule3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    private static final long serialVersionUID = 1517105344257334586L;

    @JsonIgnore
    @Column(name = "operation")
    private String operation;

    @JsonIgnore
    @Column(name = "timestamp")
    private Long timestamp;

    public BaseEntity() {
    }

    public abstract Long getId();

    public String getOperation() {
        return operation;
    }

    private void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(Long timestamp) {
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
