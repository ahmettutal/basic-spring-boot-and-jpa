package com.tutal.ahmet.springboot.jpa.controller;

import com.tutal.ahmet.springboot.jpa.Runner;
import com.tutal.ahmet.springboot.jpa.domain.Number;
import com.tutal.ahmet.springboot.jpa.model.Result;
import com.tutal.ahmet.springboot.jpa.repository.NumbersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tutal on 05.11.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Runner.class)
@WebIntegrationTest
public class NumberControllerTest {

    final String CURRENT_DATE = new java.sql.Date(new Date().getTime()).toString();
    final String BASE_URI = "http://localhost:8080";

    @Autowired
    private NumbersRepository numbersRepository;

    private RestTemplate restTemplate = new RestTemplate();

    // Test getNumber endpoint for successfully result. Create new number and get this number from getNumber endpoint.
    @Test
    public void testGetNumberDoesExist() {

        // Create a new number
        final Integer num = 112;
        final Number number = new Number(num, CURRENT_DATE);
        numbersRepository.save(number);

        // Call the API to get Number
        Number createdNumber = restTemplate.getForObject(BASE_URI + "/getNumber/?number=" + num, Number.class);

        // Verify data
        assertNotNull("The getNumber endpoint does exist test was Failed !", createdNumber);
        assertEquals("The getNumber endpoint does exist test was Failed !", number.getNumber(), createdNumber.getNumber());
        assertEquals("The getNumber endpoint does exist test was Failed !", number.getInsertDate(), createdNumber.getInsertDate());

        // Delete the created number
        numbersRepository.delete(number);
    }

    // Test getNumber endpoint for successfully result. Create new number and get this number from getNumber endpoint.
    @Test
    public void testGetNumberDoesNotExist() {

        // Do not create a new number on DB
        final Integer num = 155;

        Number createdNumber = null;
        try {
            // And call the API to get the Number
            createdNumber = restTemplate.getForObject(BASE_URI + "/getNumber/?number=" + num, Number.class);
        } catch (Exception ex) {
            // Exception is expected here
        }

        // createdNumber should be null. This number does not exist on DB
        assertTrue("The getNumber endpoint does not exist test was Failed !", createdNumber == null);
    }

    // Test addNumber endpoint for HTTP 200
    @Test
    public void testAddNumberWithOK() {

        // New number is 200
        final Integer num = 200;

        // Create number parameter for POST
        MultiValueMap<String, Integer> parameters = new LinkedMultiValueMap<>();
        parameters.add("number", num);

        // POST the new number to API with parameters
        Result response = restTemplate.postForObject(BASE_URI + "/addNumber", parameters, Result.class);

        // The response.getCode() must be 200
        assertTrue("The addNumber endpoint with Http 200 test was Failed !", response.getCode().equals(HttpStatus.OK.toString()));

        // Delete the tested number
        numbersRepository.deleteByNumber(num);
    }

    // Test addNumber endpoint for HTTP 409
    @Test
    public void testAddNumberWithCONFLICT() {

        // New number is 409
        final Integer num = 409;
        Number number = new Number(num, CURRENT_DATE);

        // Save the new number to NUMBERS Table
        numbersRepository.save(number);

        // And call API with same number for CONFLICT
        Result response = restTemplate.postForObject(BASE_URI + "/addNumber/?number=" + num, getHeaderEntity(), Result.class);

        // The response.getCode() must be 409
        assertTrue("The addNumber endpoint with Http 409 test was Failed !", response.getCode().equals(HttpStatus.CONFLICT.toString()));

        // Delete the tested number
        numbersRepository.delete(number);
    }

    // Test deleteNumber endpoint for HTTP 200
    @Test
    public void testDeleteNumberWithOK() {

        // Create a new number
        final Integer num = 200;
        Number number = new Number(num, CURRENT_DATE);

        // Save the number to NUMBERS Table
        numbersRepository.save(number);

        // Delete the new number from NUMBERS Table via API
        ResponseEntity<Result> response = restTemplate.exchange(BASE_URI + "/deleteNumber/?number=" + num, HttpMethod.DELETE, getHeaderEntity(), Result.class);

        // The response.getBody().getCode() must be 200
        assertTrue("The deleteNumber endpoint with Http 200 test was Failed !", response.getBody().getCode().equals(HttpStatus.OK.toString()));
    }

    // Test deleteNumber endpoint for HTTP 404
    @Test
    public void testDeleteNumberWithNOTFOUND() {

        // Create a new number and do not save on NUMBERS Table
        final Integer num = 200;

        // Then delete the new number from NUMBERS Table via API
        ResponseEntity<Result> response = restTemplate.exchange(BASE_URI + "/deleteNumber/?number=" + num, HttpMethod.DELETE, getHeaderEntity(), Result.class);

        // The response.getBody().getCode() must be 404
        assertTrue("The deleteNumber endpoint with Http 404 test was Failed !", response.getBody().getCode().equals(HttpStatus.NOT_FOUND.toString()));
    }

    // Test getMaxNumber endpoint
    @Test
    public void testGetMaxNumber() {

        // Create new numbers and save to DB
        final Number number1 = new Number(1, CURRENT_DATE);
        numbersRepository.save(number1);

        final Number number2 = new Number(22, CURRENT_DATE);
        numbersRepository.save(number2);

        final Number number3 = new Number(333, CURRENT_DATE);
        numbersRepository.save(number3);

        // Get max number from API
        Number maxNumber = restTemplate.getForObject(BASE_URI + "/getMaxNumber", Number.class);

        assertTrue("The getMaxNumber endpoint test was Failed !", 333 == maxNumber.getNumber());

        // Delete the test numbers
        numbersRepository.delete(number1);
        numbersRepository.delete(number2);
        numbersRepository.delete(number3);
    }

    // Test getMinNumber endpoint
    @Test
    public void testGetMinNumber() {

        // Create new numbers and save to DB
        final Number number1 = new Number(666, CURRENT_DATE);
        numbersRepository.save(number1);

        final Number number2 = new Number(77, CURRENT_DATE);
        numbersRepository.save(number2);

        final Number number3 = new Number(8, CURRENT_DATE);
        numbersRepository.save(number3);

        // Get min number from API
        Number maxNumber = restTemplate.getForObject(BASE_URI + "/getMinNumber", Number.class);

        assertTrue("The getMinNumber endpoint test was Failed !", 8 == maxNumber.getNumber());

        // Delete the test numbers
        numbersRepository.delete(number1);
        numbersRepository.delete(number2);
        numbersRepository.delete(number3);
    }

    // Test getAllNumbers endpoint
    @Test
    public void testGetAllNumbers() {

        // Create new numbers and save to DB
        final Number number1 = new Number(313, CURRENT_DATE);
        numbersRepository.save(number1);

        final Number number2 = new Number(472, CURRENT_DATE);
        numbersRepository.save(number2);

        // Call the API for get all numbers
        List<Number> createdNumbers = restTemplate.getForObject(BASE_URI + "/getAllNumbers", List.class);

        // Assert createdNumbers size
        assertTrue("The getAllNumbers endpoint test was Failed !", createdNumbers.size() == 2);

        // Delete the tested numbers
        numbersRepository.delete(number1);
        numbersRepository.delete(number2);
    }

    private HttpEntity<?> getHeaderEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

}
