package com.tutal.ahmet.springboot.jpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tutal on 04.11.2016.
 */

public interface NumbersRepository extends CrudRepository<Numbers, Long> {

    Numbers findByNumber(int number);

    @Transactional
    Long deleteByNumber(int number);

    Numbers findTopByOrderByNumberDesc();

    Numbers findTopByOrderByNumberAsc();

    Iterable<Numbers> findAll(Sort sort);

}
