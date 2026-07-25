package com.cognizant.spring_learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cognizant.spring_learn.model.Country;

@SpringBootApplication
public class SpringLearnApplication {

	public static void displayCountry() {

		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

		Country country = context.getBean("country", Country.class);

		System.out.println("Country : " + country);
	}

	public static void main(String[] args) {

		System.out.println("Application Started");

		SpringApplication.run(SpringLearnApplication.class, args);

		displayCountry();

		System.out.println("Application Ended");
	}
}