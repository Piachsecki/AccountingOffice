package org.example;


import org.example.adapter.out.database.configuration.ApplicationConfiguration;
import org.example.application.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        customerService.deleteAll();
    }
}