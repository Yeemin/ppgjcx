package com.yeemin.ppgjcx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PpgjcxApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpgjcxApplication.class, args);
	}

}
