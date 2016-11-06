package com.tutal.ahmet.springboot.jpa.controller;

import com.tutal.ahmet.springboot.jpa.domain.Number;
import com.tutal.ahmet.springboot.jpa.model.Result;
import com.tutal.ahmet.springboot.jpa.repository.NumbersRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by tutal on 04.11.2016.
 */

@RestController
public class NumberController {

    @Autowired
    NumbersRepository numbersRepository;

    /**
     * Get Number
     *
     * @param num
     * @return Number Object
     * @throws Exception
     */
    @RequestMapping(value = "/getNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Number> getNumber(@RequestParam(value = "number") Integer num) throws Exception {
        Number number = numbersRepository.findByNumber(num);

        // if the number does not exist
        if (number == null)
            throw new NotFoundException("The number does not exist on NUMBERS Table!");

        return ResponseEntity.ok(number);
    }

    /**
     * Add Number
     *
     * @param num
     * @return Result
     */
    @RequestMapping(value = "/addNumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> addNumber(@RequestParam(value = "number") Integer num) {

        Number oldRecord = numbersRepository.findByNumber(num);

        // if the number already exists
        if (oldRecord != null)
            return ResponseEntity.ok(new Result("409", "The Number already exists on NUMBERS Table"));

        Date currentDate = new java.sql.Date(new Date().getTime());

        Number number = new Number(num, currentDate.toString());

        numbersRepository.save(number);

        return ResponseEntity.ok(new Result("200", "The Number was added to NUMBERS Table"));
    }

    /**
     * Delete Number
     *
     * @param num
     * @return Result
     */
    @RequestMapping(value = "/deleteNumber", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> deleteNumber(@RequestParam(value = "number") Integer num) {

        Number oldRecord = numbersRepository.findByNumber(num);

        // if the number was not found
        if (oldRecord == null)
            return ResponseEntity.ok(new Result("404", "The Number was not found on NUMBERS Table"));

        numbersRepository.deleteByNumber(num);

        return ResponseEntity.ok(new Result("200", "The number was deleted from NUMBERS Table"));
    }

    /**
     * Get Max Number
     *
     * @return Get Max Number Object
     */
    @RequestMapping(value = "/getMaxNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Number> getMaxNumber() {

        Number number = numbersRepository.findTopByOrderByNumberDesc();

        return ResponseEntity.ok(number);
    }

    /**
     * Get Min Number
     *
     * @return Get Min Number Object
     */
    @RequestMapping(value = "/getMinNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Number> getMinNumber() {

        Number number = numbersRepository.findTopByOrderByNumberAsc();

        return ResponseEntity.ok(number);
    }

    /**
     * Get All Numbers
     *
     * @param orderby
     * @return Collection of Numbers
     */
    @RequestMapping(value = "/getAllNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Number>> findAllNumbers(@RequestParam(value = "orderby", defaultValue = "ASC") String orderby) {

        // Default sort direction is ASC
        Direction direction = Direction.ASC;

        // if parameter was set to DESC then change the sort direction
        if (orderby.equals("DESC"))
            direction = Direction.DESC;

        List<Number> numbers = numbersRepository.findAll(new Sort(direction, "number"));

        return ResponseEntity.ok(numbers);
    }

}
