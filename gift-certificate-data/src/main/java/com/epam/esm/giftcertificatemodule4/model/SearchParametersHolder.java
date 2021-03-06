package com.epam.esm.giftcertificatemodule4.model;

public class SearchParametersHolder {
    Long id;
    String tagName;
    String name;
    String description;
    String sortBy;
    String sortOrder;
    Boolean highestPrice;

    public SearchParametersHolder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Boolean highestPrice) {
        this.highestPrice = highestPrice;
    }

    @Override
    public String toString() {
        return "SearchParametersHolder{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", highestPrice=" + highestPrice +
                '}';
    }
}
