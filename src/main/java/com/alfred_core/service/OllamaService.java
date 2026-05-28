package com.alfred_core.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaService {

	private final RestTemplate restTemplate;

	public OllamaService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String generate(String prompt) {

		String url = "http://localhost:11434/api/generate";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> body = new HashMap<>();
		body.put("model", "llama3:8b");
		body.put("prompt", prompt);
		body.put("stream", false);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		Map response = restTemplate.postForObject(url, request, Map.class);

		return response.get("response").toString();
	}
}