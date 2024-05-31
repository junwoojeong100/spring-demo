package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

	@SpringBootApplication
	@RestController
	public class DemoApplication {

	public static void main(String[] args) {		
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	//@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		LocalDateTime now = LocalDateTime.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(now.format(formatter)) + " '/hello' called.");
		return String.format("Hello %s!", name);
	}

}
            
