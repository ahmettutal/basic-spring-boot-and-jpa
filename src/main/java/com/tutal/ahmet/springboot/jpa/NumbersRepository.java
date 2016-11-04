package com.tutal.ahmet.springboot.jpa;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by tutal on 04.11.2016.
 */
public interface NumbersRepository extends CrudRepository<Numbers, Long> {

    Long deleteByNumber(float number);

}
