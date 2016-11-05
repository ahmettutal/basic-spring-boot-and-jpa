package com.tutal.ahmet.springboot.jpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tutal on 04.11.2016.
 */

public interface NumbersRepository extends CrudRepository<Number, Long> {

    Number findByNumber(int number);

    @Transactional
    Long deleteByNumber(int number);

    Number findTopByOrderByNumberDesc();

    Number findTopByOrderByNumberAsc();

    Iterable<Number> findAll(Sort sort);

}
