package org.lemon.yhj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LemonApplication {
	private static final Logger log = LoggerFactory.getLogger(LemonApplication.class);

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		SpringApplication.run(LemonApplication.class, args);
		long period = System.currentTimeMillis() - start;
		log.error("LemonApplication start successfully in {} ms.",period);
	}
}
