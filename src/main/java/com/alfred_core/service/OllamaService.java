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

    private final RestTemplate restTemplate; //REST TEMPLATE = USER (SINGLE FOR WHOLE APP)

    public OllamaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generate(String prompt) {

        String url = "http://localhost:11434/api/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //TELLS OLLAMA USER SENDING JSON

        Map<String, Object> body = new HashMap<>();

        body.put("model", "qwen3:4b-instruct");
        body.put("prompt", prompt);
        body.put("stream", false);
        
//        BECOMES
//        {
//        	  "model": "qwen3:4b-instruct"
//        	   Tells Ollama which model to use.
//        	}
        
//        Streaming
//        body.put("stream", false);
//
//        Without streaming:
//
//        Wait...
//        Receive complete answer
//
//        With streaming: Receive word by word

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(
                url,
                request,
                Map.class
        );
        
//        OLLAMA SENDS SOMETHING LIKE
//        {
//        	  "model":"qwen3:4b-instruct",
//        	  "created_at":"...",
//        	  "response":"Polymorphism is the ability...",
//        	  "done":true
//        	}

        return response.get("response").toString();
    }
}