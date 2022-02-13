package com.mediacare.util;

import java.time.Instant;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClearJwtTask implements Runnable {

	@Autowired
	private Queue<String> jwtQueue;
	
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
