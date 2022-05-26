package com.mediacare.mvc.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mediacare.mvc.dto.PredictionDto;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MlService {

	private final WebClient mlService;	
	
	
	public String callMlModel(PredictionDto predictionDto) {
		
		String [] patient= {"Not Affected","Affected"};
		Integer result =mlService.post()
				.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(predictionDto),PredictionDto.class)
				.retrieve()
				.bodyToMono(Integer.class).block();
		
		return patient[result];
	}
}
