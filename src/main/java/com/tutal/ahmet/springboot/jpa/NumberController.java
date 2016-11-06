package com.tutal.ahmet.springboot.jpa;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "/getNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Number> getNumber(@RequestParam(value = "number") Integer num) throws Exception {
        Number number = numbersRepository.findByNumber(num);

        if (number == null)
            throw new NotFoundException("The number does not exist on NUMBERS Table!");

        return ResponseEntity.ok(number);
    }

    @RequestMapping(value = "/addNumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> addNumber(@RequestParam(value = "number") Integer num) {

        Number oldRecord = numbersRepository.findByNumber(num);
        if (oldRecord != null)
            return ResponseEntity.ok(new Result("409", "The Number already exists on NUMBERS Table"));

        Date currentDate = new java.sql.Date(new Date().getTime());

        Number number = new Number(num, currentDate.toString());

        numbersRepository.save(number);

        return ResponseEntity.ok(new Result("200", "The Number was added to NUMBERS Table"));
    }

    @RequestMapping(value = "/deleteNumber", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> deleteNumber(@RequestParam(value = "number") Integer num) {

        Number oldRecord = numbersRepository.findByNumber(num);
        if (oldRecord == null)
            return ResponseEntity.ok(new Result("404", "The Number was not found on NUMBERS Table"));

        numbersRepository.deleteByNumber(num);

        return ResponseEntity.ok(new Result("200", "The number was deleted from NUMBERS Table"));
    }

    @RequestMapping(value = "/getMaxNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Number> getMaxNumber() {

        Number number = numbersRepository.findTopByOrderByNumberDesc();

        return ResponseEntity.ok(number);
    }

    @RequestMapping(value = "/getMinNumber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Number> getMinNumber() {

        Number number = numbersRepository.findTopByOrderByNumberAsc();

        return ResponseEntity.ok(number);
    }

    @RequestMapping(value = "/getAllNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Number>> findAllNumbers(@RequestParam(value = "orderby", defaultValue = "ASC") String orderby) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (orderby.equals("DESC"))
            direction = Sort.Direction.DESC;

        Iterable<Number> numbers = numbersRepository.findAll(new Sort(direction, "number"));

        return ResponseEntity.ok(numbers);
    }

}
