package com.tutal.ahmet.springboot.jpa;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by tutal on 04.11.2016.
 */

@SpringBootApplication
public class Runner {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Runner.class)
                .run(args);
    }
}
