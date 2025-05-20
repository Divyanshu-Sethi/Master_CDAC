package com.example.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class masterApplication {

	public static void main(String[] args) {
		SpringApplication.run(masterApplication.class, args);
	}

}
//EndPoint for Leaves Master => http://localhost:8080/leaves
//EndPoint for User Master => http://localhost:8080/usertype
//EndPoint for Designation Master => http://localhost:8080/designations
//EndPoint for Qualification Master =>http://localhost:8080/qualifications
//EndPoint for University Master =>http://localhost:8080/university