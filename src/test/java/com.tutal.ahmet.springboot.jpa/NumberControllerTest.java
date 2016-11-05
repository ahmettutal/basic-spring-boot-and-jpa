package com.tutal.ahmet.springboot.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tutal on 05.11.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Runner.class)
@WebIntegrationTest
public class NumberControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    final String CURRENT_DATE = new java.sql.Date(new Date().getTime()).toString();
    final String BASE_URL = "http://localhost:8080";

    @Autowired
    private NumbersRepository numbersRepository;

    private RestTemplate restTemplate = new RestTemplate();

    // Test getNumber endpoint for successfully result. Create new number and get this number from getNumber endpoint.
    @Test
    public void testGetNumberDoesExist() {

        // Create a new number
        int num = 112;
        Number number = new Number(num, CURRENT_DATE);
        numbersRepository.save(number);

        // Call the API to get Number
        Number createdNumber = restTemplate.getForObject(BASE_URL + "/getNumber/?number=" + num, Number.class);

        // Verify data
        assertNotNull(createdNumber);
        assertEquals(number.getNumber(), createdNumber.getNumber());
        assertEquals(number.getInsertDate(), createdNumber.getInsertDate());

        //Delete the created number
        numbersRepository.deleteByNumber(num);
    }

    // Test getNumber endpoint for successfully result. Create new number and get this number from getNumber endpoint.
    @Test
    public void testGetNumberDoesNotExist() {

        // Do not create a new number on DB
        int num = 155;

        Number createdNumber = null;
        try {
            // And call the API to get the Number
            createdNumber = restTemplate.getForObject(BASE_URL + "/getNumber/?number=" + num, Number.class);
        } catch (Exception ex) {
            // Exception is expected here
        }

        // createdNumber should be null. This number does not exist on DB
        assertTrue(createdNumber == null);
    }

    // Test getAllNumbers endpoint
    @Test
    public void testGetAllNumbers() {

        // Create new numbers
        Number number1 = new Number(313, CURRENT_DATE);
        numbersRepository.save(number1);

        Number number2 = new Number(472, CURRENT_DATE);
        numbersRepository.save(number2);

        // Call the API for numbers
        Iterable<Number> createdNumbers = restTemplate.getForObject(BASE_URL + "/getAllNumbers", Iterable.class);

        // Assert createdNumbers size
        assertTrue(Lists.newArrayList(createdNumbers).size() == 2);

    }

}
