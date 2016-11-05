package com.tutal.ahmet.springboot.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by tutal on 05.11.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Runner.class)
@WebIntegrationTest
public class NumberControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private NumbersRepository numbersRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetNumbersApi() {

        // Create a new number
        int num = 112;
        Number number = new Number(num, new java.sql.Date(new Date().getTime()).toString());
        numbersRepository.save(number);

        // Call to the API to get Number
        Number apiResponse = restTemplate.getForObject("http://localhost:8080/getNumber/?number=" + num, Number.class);

        // Verify data
        assertNotNull(apiResponse);
        assertEquals(number.getNumber(), apiResponse.getNumber());
        assertEquals(number.getInsertDate(), apiResponse.getInsertDate());

        //Delete the Test data created
        numbersRepository.deleteByNumber(num);
    }


}
