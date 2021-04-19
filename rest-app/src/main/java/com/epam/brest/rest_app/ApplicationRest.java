package com.epam.brest.rest_app;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = "com.epam.brest")
public class ApplicationRest extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationRest.class).web(WebApplicationType.SERVLET).run(args);
    }

}
