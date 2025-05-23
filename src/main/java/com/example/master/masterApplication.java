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
//EndPoint for Leaves Master => http://localhost:8080/leaves                   (UPDATED)
//EndPoint for User Master => http://localhost:8080/usertype                   (UPDATED)
//EndPoint for Designation Master => http://localhost:8080/designations        (UPDATED)
//EndPoint for Qualification Master =>http://localhost:8080/qualifications     (UPDATED)
//EndPoint for University Master =>http://localhost:8080/university-type       (UPDATED)