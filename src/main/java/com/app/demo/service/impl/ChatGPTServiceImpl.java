package com.app.demo.service.impl;

import com.app.demo.service.ChatGPTService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.app.demo.converter.MusicConverter;
import com.app.demo.dto.request.MusicRequestDTO;
import com.app.demo.dto.response.MusicResponseDTO;
import com.app.demo.entity.Music;
import com.app.demo.repository.MusicRepository;
import com.app.demo.service.MusicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatGPTServiceImpl implements ChatGPTService {

    private final MusicService musicService;

    // 추천음악 저장까지 로직
    @Override
    public List<Music> processMusicRecommendations(String tagString, String tagStringEmotion, String preferString) {
        String prompt = createPrompt(tagString, tagStringEmotion, preferString);
        String response = chatGPT(prompt);
        List<MusicRequestDTO.MusicContentDTO> searchMusicDTOs = parseResponse(response);

        MusicRequestDTO.SaveMusicDTO saveMusicDTO = MusicConverter.toSaveMusicDTO(searchMusicDTOs);
        return musicService.saveMusic(saveMusicDTO);
    }

    @Override
    public String createPrompt(String tagString, String tagStringEmotion, String preferString) {
        String prompt = "";
        prompt = String.format(
                "제가 '%s' 감정을 느끼고 있을 때 들으면 좋은 노래가 있을까요? 저는 그 감정을 느낄 때 평소에 '%s'한 음악을 즐겨 듣습니다. '%s'의 음악을 즐깁니다. '%s' 보다는 '%s'를 조금 더 중점으로 하여 좋은 음악 20개 추천 부탁드립니다. 되도록 한국 음악으로 추천해주세요. 노래는 아래의 형식으로 추천해주세요 (노래 하나의 정보는 { } 묶습니다. 줄바꿈 없이) : [{'가수명', '노래제목'},{'가수명', '노래제목'}, ... ,{'가수명', '노래제목'},{'가수명', '노래제목'}]",
                tagString, tagStringEmotion, preferString, preferString, tagString);
        return prompt;
    }

    @Override
    public List<Music> processMusicRecommendations(String preferString) {
        String prompt = createPrompt(preferString);
        String response = chatGPT(prompt);
        List<MusicRequestDTO.MusicContentDTO> searchMusicDTOs = parseResponse(response);

        MusicRequestDTO.SaveMusicDTO saveMusicDTO = MusicConverter.toSaveMusicDTO(searchMusicDTOs);
        return musicService.saveMusic(saveMusicDTO);
    }

    @Override
    public String createPrompt(String preferString) {
        return String.format(
                "저는 '%s'장르의 음악을 즐깁니다. 첫번째 장르의 음악 5개, 두번째 장르의 음악 3개, 세번째 장르의 음악 1개 추천 부탁드립니다. 되도록 한국 음악으로 추천해주세요. 노래는 아래의 형식으로 추천해주세요 (노래 하나의 정보는 { } 묶습니다. 줄바꿈 없이) : [{'가수명', '노래제목'},{'가수명', '노래제목'}, ... ,{'가수명', '노래제목'},{'가수명', '노래제목'}]",
                preferString);
    }


    @Override
    public String chatGPT(String prompt) {
        String url = System.getenv("GPT_URL");
        String apiKey = System.getenv("GPT_KEY");
        String model = "gpt-3.5-turbo";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String jsonBody = String.format(
                "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                model, prompt.replaceAll("\"", "\\\\\"")
        );

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return extractMessageFromJSONResponse(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting to OpenAI: " + e.getMessage();
        }

    }

    @Override
    public String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }

    @Override
    public List<MusicRequestDTO.MusicContentDTO> parseResponse(String jsonResponse) {
        List<MusicRequestDTO.MusicContentDTO> musicList = new ArrayList<>();
        List<List<String>> parsedData = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(jsonResponse);

        while (matcher.find()) {
            String matchedGroup = matcher.group(1);
            // 각 항목 내의 쉼표로 구분된 값들을 추출하여 리스트로 변환
            String[] items = matchedGroup.split(",\\s*'");
            List<String> dataList = new ArrayList<>();
            for (String item : items) {
                dataList.add(item.replace("'", "").trim());  // 작은따옴표 제거
            }
            parsedData.add(dataList);
        }

        for (List<String> item : parsedData) {
            String artist = item.get(0);
            String title = item.get(1);
            String searchQuery = title + "/" + artist;

            // 음악 검색
            List<MusicResponseDTO.MusicSearchContentDTO> searchResults = musicService.searchMusic(searchQuery, 0);
            if (!searchResults.isEmpty()) {
                // 첫 번째 음악 선택
                MusicResponseDTO.MusicSearchContentDTO firstResult = searchResults.get(0);
                MusicRequestDTO.MusicContentDTO musicContentDTO = MusicConverter.searchContentDTOToMusicContentDTO(firstResult);
                musicList.add(musicContentDTO);
            } else {
                // 없으면 pictureKey = null 처리
                MusicResponseDTO.MusicSearchContentDTO firstResult = new MusicResponseDTO.MusicSearchContentDTO(title, artist, "NO_IMAGE");
                MusicRequestDTO.MusicContentDTO musicContentDTO = MusicConverter.searchContentDTOToMusicContentDTO(firstResult);
                musicList.add(musicContentDTO);
            }
        }
        return musicList;
    }

    // 파싱 잘하는지 테스트
    /*
    private static boolean parseResponseE(String jsonResponse) throws JsonProcessingException {
        List<MusicRequestDTO.MusicContentDTO> musicList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        List<List<String>> dataList = new ArrayList<>();
        dataList = objectMapper.readValue(jsonResponse, new TypeReference<List<List<String>>>() {});

        for (List<String> item : dataList) {
            String artist = item.get(0);
            String title = item.get(1);
            String searchQuery = title + "/" + artist;
            System.out.println(searchQuery);
        }

        return false;
    }

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(parseResponseE("[{'아이유', '밤편지'},{'폴킴', '비'},{'볼빨간사춘기', '여행'},{'헤이즈', '비도 오고 그래서 (Feat. 신용재)'},{'마마무', '별이 빛나는 밤'}]"));
    }

     */
}
