package com.tutal.ahmet.springboot.jpa;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by tutal on 04.11.2016.
 */

@SpringBootApplication
@EnableJpaRepositories
public class Runner {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Runner.class)
                .run(args);
    }
}
