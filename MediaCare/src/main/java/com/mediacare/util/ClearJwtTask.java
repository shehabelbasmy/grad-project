package com.mediacare.util;

import java.util.Queue;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClearJwtTask implements Runnable {

	private final Queue<String> jwtQueue;
	
	@Override
	public void run() {
		this.jwtQueue.remove();
		
		if(jwtQueue.isEmpty()) {
			throw new RuntimeException();
		}
	}

}
