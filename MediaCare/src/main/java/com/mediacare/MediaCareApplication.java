package com.mediacare;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediaCareApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MediaCareApplication.class, args);
	}
	
	@Bean
	public Queue<String> jwtQueue(){
		return new ConcurrentLinkedDeque<String>();
	}
}
