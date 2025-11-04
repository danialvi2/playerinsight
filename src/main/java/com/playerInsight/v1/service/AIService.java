package com.playerInsight.v1.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FLASK_URL = "http://localhost:2000/analyze";

    public Object analyzeTexts(List<String> texts){
        try {
            //Lage JSON-body
            String jsonBody = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(Map.of("texts", texts));


            //Sett headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //Lag requests
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            //Send til Flask og få svar
            ResponseEntity<Object> response = restTemplate.postForEntity(FLASK_URL, request, Object.class);

            return response.getBody();

        } catch (Exception e){
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Feil ved kontakt med AI-modulen: " +e.getMessage());
            return error;
        }
    }
}
