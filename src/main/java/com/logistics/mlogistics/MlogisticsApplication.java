package com.logistics.mlogistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MlogisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlogisticsApplication.class, args);
	}

}
