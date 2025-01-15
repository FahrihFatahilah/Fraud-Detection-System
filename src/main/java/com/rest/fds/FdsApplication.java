package com.rest.fds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class FdsApplication {
	private static final Logger logger = LoggerFactory.getLogger(FdsApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(FdsApplication.class, args);
		logger.info("Ponooooo {} ", (Object) args);
	}

}
