package com.labym.flood.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<E,DTO,ID extends Serializable> {

    void create(E enity);

    Optional<DTO> findById(ID id);

    List<DTO> findAll();

    void delete(ID id);

    void delete(E entity);


}
