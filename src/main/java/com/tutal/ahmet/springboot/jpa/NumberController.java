package com.tutal.ahmet.springboot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by tutal on 04.11.2016.
 */

@RestController
public class NumberController {

    @Autowired
    NumbersRepository numbersRepository;

    @RequestMapping(value = "/addNumber", method = RequestMethod.PUT, produces = {"application/json"})
    public ResponseEntity<Result> addNumber(@RequestParam(value = "number") int num) {

        Numbers oldRecord = numbersRepository.findByNumber(num);
        if (oldRecord != null)
            return ResponseEntity.ok(new Result("409", "This Number already exists on Numbers Table"));

        Numbers number = new Numbers();
        number.setNumber(num);

        Date today = new Date();
        Date currentDate = new java.sql.Date(today.getTime());

        number.setInsertDate(currentDate.toString());

        numbersRepository.save(number);

        return ResponseEntity.ok(new Result("200", "The number added to Numbers Table"));
    }

    @RequestMapping(value = "/deleteNumber", method = RequestMethod.DELETE, produces = {"application/json"})
    public ResponseEntity<Result> deleteNumber(@RequestParam(value = "number") int num) {

        Numbers oldRecord = numbersRepository.findByNumber(num);
        if (oldRecord == null)
            return ResponseEntity.ok(new Result("404", "This Number was not found on Numbers Table"));

        numbersRepository.deleteByNumber(num);

        return ResponseEntity.ok(new Result("200", "The number deleted on Numbers Table successfully"));
    }

    @RequestMapping(value = "/getMaxNumber", produces = {"application/json"})
    public ResponseEntity<Numbers> getMaxNumber() {

        Numbers number = numbersRepository.findTopByOrderByNumberDesc();

        return ResponseEntity.ok(number);
    }

    @RequestMapping(value = "/getMinNumber", produces = {"application/json"})
    public ResponseEntity<Numbers> getMinNumber() {

        Numbers number = numbersRepository.findTopByOrderByNumberAsc();

        return ResponseEntity.ok(number);
    }

    @RequestMapping(value = "/numbers", produces = {"application/json"})
    public ResponseEntity<Iterable<Numbers>> findAllNumbers(@RequestParam(value = "orderby", defaultValue = "ASC") String orderby) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (orderby.equals("DESC"))
            direction = Sort.Direction.DESC;

        Iterable<Numbers> numbers = numbersRepository.findAll(new Sort(direction, "number"));

        return ResponseEntity.ok(numbers);
    }

}
