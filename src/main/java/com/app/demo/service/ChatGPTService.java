package com.app.demo.service;

import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.entity.Music;

import java.util.List;

public interface ChatGPTService {
    public List<Music> processMusicRecommendations(String tagString, String tagStringEmotion, String preferString, String aiEmotionHigh);
    public String createPrompt(String tagString, String tagStringEmotion, String preferString, String aiEmotionHigh);
    public List<Music> processMusicRecommendations(String preferString);
    public String createPrompt(String preferString);

    public String chatGPT(String prompt);
    public String extractMessageFromJSONResponse(String response);
    public List<MusicRequestDTO.MusicContentDTO> parseResponse(String jsonResponse);

}
