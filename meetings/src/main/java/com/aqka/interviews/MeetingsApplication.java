package com.aqka.interviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan( basePackages = { "com.aqka.interviews" } )
@SpringBootApplication
public class MeetingsApplication {

	/**
	 *
	 * Test for: AKQA - Test
	 * by Mihai Stefan 17 May 2022
	 *
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MeetingsApplication.class, args);
	}

}
