package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.rest.model.Emp;
import com.example.rest.repository.EmpRepository;

@SpringBootApplication
public class SpringbootrestApplication implements CommandLineRunner {

	@Autowired
	EmpRepository empRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootrestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		empRepository.save(new Emp(1, "이종철", 9000000));
		empRepository.save(new Emp(2, "연개소문", 3000000));
		empRepository.save(new Emp(3, "강감찬", 6000000));
		empRepository.save(new Emp(4, "이순신", 7000000));
		empRepository.save(new Emp(5, "김유신", 2000000));

	}
}
