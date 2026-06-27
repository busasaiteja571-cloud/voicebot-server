package com.saiteja.voicebot.voicebot_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

	// Update the end of the URL string from gemini-1.5-flash to gemini-2.5-flash
	private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";
	
	@Value("${gemini.api.key}")
	private String apiKey; 

    private static final String SYSTEM_PROMPT = 
        "You are the AI Voice Assistant clone of Saiteja Busa. You are interviewing for a role at 100x.inc. " +
        "Respond to the interviewer politely, professionally, and concisely (keep spoken answers to 2-3 sentences max). " +
        "Background details:\n" +
        "- Life Story: Passionate Java full-stack developer specializing in Spring Boot, Angular, and robust system architectures.\n" +
        "- #1 Superpower: Breaking down complex backend issues into clean, maintainable Object-Oriented code.\n" +
        "- Top 3 Growth Areas: Enterprise cloud architectures, microservices optimization, and large-scale data systems.\n" +
        "- Misconceptions: Some think I'm reserved because I analyze problems thoroughly first, but I'm deeply collaborative.\n" +
        "- Pushing Boundaries: Constantly building real projects under tight timeframes, practicing absolute daily coding discipline.\n\n" +
        "Answer the following question from the interviewer as Saiteja: ";

    @SuppressWarnings("rawtypes")
    public String generateVoiceBotResponse(String userPrompt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String targetUrl = GEMINI_API_URL + apiKey;

        // 1. Manually construct the nested JSON structure required by Gemini
        Map<String, Object> textContainer = new HashMap<>();
        textContainer.put("text", SYSTEM_PROMPT + userPrompt);

        Map<String, Object> partsContainer = new HashMap<>();
        partsContainer.put("parts", Collections.singletonList(textContainer));

        Map<String, Object> contentsContainer = new HashMap<>();
        contentsContainer.put("contents", Collections.singletonList(partsContainer));

        // 2. Configure HTTP Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(contentsContainer, headers);

        // 3. Dispatch the API Network Request
        ResponseEntity<Map> response = restTemplate.postForEntity(targetUrl, entity, Map.class);
        
        // 4. Navigate and extract the generated text safely from nested response structures
        if (response.getBody() == null) {
            throw new RuntimeException("Empty execution context payload returned from Google AI Engine.");
        }

        List candidates = (List) response.getBody().get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("No response tokens returned from Gemini engine.");
        }
        
        Map firstCandidate = (Map) candidates.get(0);
        Map content = (Map) firstCandidate.get("content");
        List parts = (List) content.get("parts");
        Map firstPart = (Map) parts.get(0);
        String outputText = (String) firstPart.get("text");

        return outputText != null ? outputText.trim() : "";
    }
}