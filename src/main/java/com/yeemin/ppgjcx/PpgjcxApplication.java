package com.yeemin.ppgjcx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class PpgjcxApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpgjcxApplication.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public ApplicationRunner initDatabase() {
		return args -> {

		};
	}

}
