package com.tutal.ahmet.springboot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tutal on 04.11.2016.
 */

@RestController
public class NumberController {

    @Autowired
    NumbersRepository numbersRepository;

    @RequestMapping(value = "/addNumber", method = RequestMethod.GET, produces = {"application/json"})
    public Object addNumber(@RequestParam(value = "number") int num) {

        Numbers number = new Numbers();
        number.setNumber(num);

        Date today = new Date();
        Date currentDate = new java.sql.Date(today.getTime());

        number.setInsertDate(currentDate.toString());

        numbersRepository.save(number);

        return number;
    }

}
