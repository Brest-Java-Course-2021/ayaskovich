package com.epam.brest.web_app;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "com.epam.brest")
public class Example {

    public static void main(String[] args) {

        new SpringApplicationBuilder(Example.class).web(WebApplicationType.SERVLET).run(args);

    }

}
