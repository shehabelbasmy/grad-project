package com.mediacare.util;

import java.time.Instant;
import java.util.Queue;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClearJwtTask implements Runnable {

	private final Queue<String> jwtQueue;
	
	@Override
	public void run() {
		System.out.println("delete"+Instant.now());
		this.jwtQueue.remove();
		
		if(jwtQueue.isEmpty()) {
			System.out.println("throw Exception");
			throw new RuntimeException();
		}
	}

}
