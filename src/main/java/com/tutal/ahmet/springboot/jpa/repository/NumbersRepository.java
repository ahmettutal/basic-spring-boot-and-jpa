package com.tutal.ahmet.springboot.jpa.repository;

import com.tutal.ahmet.springboot.jpa.domain.Number;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tutal on 04.11.2016.
 */

public interface NumbersRepository extends CrudRepository<Number, Long> {

    /**
     * Query Object by number
     *
     * @param number
     * @return Number Object
     */
    Number findByNumber(int number);

    /**
     * Delete Object by number
     *
     * @param number
     * @return Long Object
     */
    @Transactional
    Long deleteByNumber(int number);

    /**
     * Query Object for max number
     *
     * @return Number Object
     */
    Number findTopByOrderByNumberDesc();

    /**
     * Query Object for min number
     *
     * @return Number Object
     */
    Number findTopByOrderByNumberAsc();

    /**
     * Query Object Collection by sort
     *
     * @param sort
     * @return Collection of Numbers
     */
    List<Number> findAll(Sort sort);

}
