package com.app.demo.service.impl;

import com.app.demo.dto.response.AiEmotionResponse;
import com.app.demo.entity.AiEmotion;
import com.app.demo.repository.AiEmotionRepository;
import com.app.demo.service.AiEmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiEmotionServiceImpl implements AiEmotionService {

    AiEmotionRepository aiEmotionRepository;


    @Override
    public AiEmotion getAiEmotion(String content) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://34.64.147.34:5000/predict";
        Map<String, String> body = new HashMap<>();
        body.put("text", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        AiEmotionResponse.BertResponse response = restTemplate.postForObject(url, entity, AiEmotionResponse.BertResponse.class);

        if(response != null){
            int sum = (int) Math.round(response.getProbabilities().get(0))
                    + (int) Math.round(response.getProbabilities().get(1))
                    + (int) Math.round(response.getProbabilities().get(2));

            AiEmotion aiEmotion = AiEmotion.builder()
                    .happy((int) Math.round(response.getProbabilities().get(0)))
                    .angry((int) Math.round(response.getProbabilities().get(1)))
                    .surprise((int) Math.round(response.getProbabilities().get(2)))
                    .sad(100-sum)
                    .build();

            aiEmotionRepository.save(aiEmotion);
            return aiEmotion;
        }
        return null;
    }
}
