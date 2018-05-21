package com.labym.flood.common.service.impl;

import com.labym.flood.common.service.BaseService;
import com.labym.flood.common.service.EntityMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseServiceImpl<E, D, I extends Serializable> implements BaseService<E, D, I> {

    private JpaRepository<E, I> repository;
    private EntityMapper<D, E> entityMapper;

    public BaseServiceImpl(JpaRepository<E, I> repository, EntityMapper<D, E> entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    @Override
    public void create(E enity) {
        repository.save(enity);
    }

    @Override
    public Optional<D> findById(I i) {
        return repository.findById(i)
                .map(entityMapper::toDto)
                .map(Optional::ofNullable)
                .orElse(Optional.empty());

    }

    @Override
    public List<D> findAll() {
        return repository
                .findAll()
                .stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(I i) {
        repository.deleteById(i);
    }

    public void delete(E entity){
        repository.delete(entity);
    }
}
