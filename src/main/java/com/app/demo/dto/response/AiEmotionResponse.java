package com.app.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class AiEmotionResponse {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class BertResponse {
        private String inputText;
        private String predictedEmotion;
        private List<Double> probabilities;
    }
}
