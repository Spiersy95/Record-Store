package com.northcoders.RecordStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecordStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordStoreApplication.class, args);
	}

}
