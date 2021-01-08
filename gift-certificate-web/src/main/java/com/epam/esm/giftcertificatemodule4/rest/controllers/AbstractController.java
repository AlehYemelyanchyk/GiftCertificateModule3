package com.epam.esm.giftcertificatemodule4.rest.controllers;

import com.epam.esm.giftcertificatemodule4.entity.BaseEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class AbstractController<T extends BaseEntity> {

    private static final int PAGE = 0;
    private static final int SIZE = 5;

    public abstract List<EntityModel<T>> findAll(int page, int size);

    public abstract EntityModel<T> findById(Long id);

    protected List<EntityModel<T>> getEntityModels(List<T> returnObject) {
        return returnObject.stream()
                .map((T e) -> {
                    WebMvcLinkBuilder linkToFindById = linkTo(methodOn(this.getClass()).findById(e.getId()));
                    EntityModel<T> entityModel = EntityModel.of(e);
                    entityModel.add(linkToFindById.withRel(getResultById()));
                    return entityModel;
                })
                .collect(Collectors.toList());
    }

    protected EntityModel<T> getEntityModel(T returnObject) {
        WebMvcLinkBuilder linkToFindAll = linkTo(methodOn(this.getClass()).findAll(PAGE, SIZE));
        EntityModel<T> entityModel = EntityModel.of(returnObject);
        entityModel.add(linkToFindAll.withRel(getAllResults()));
        return entityModel;
    }

    protected abstract String getResultById();

    protected abstract String getAllResults();
}
