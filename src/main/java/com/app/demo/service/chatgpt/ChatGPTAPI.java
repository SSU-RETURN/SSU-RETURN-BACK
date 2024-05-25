package com.app.demo.service.chatgpt;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public class ChatGPTAPI {

    public static String chatGPT(String prompt) {
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

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }

    static String tags = "happy";
    static String tags_ = "excited";
    static String[] prefer = { "ballad", "pop" }; // 주의해야할듯..

    static String tagString = String.join(", ", tags);
    static String preferString = String.join(", ", prefer);
    static String prompt = String.format(
            "제가 '%s' 감정을 느끼고 있을 때 들으면 좋은 노래가 있을까요? 저는 그 감정을 느낄 때 평소에 '%s'한 음악을 즐겨 듣습니다. '%s'의 음악을 즐깁니다. '%s' 보다는 '%s'를 조금 더 중점으로 하여 좋은 음악 5개 추천 부탁드립니다. 되도록 한국 음악으로 추천해주세요. 노래는 아래의 형식으로 추천해주세요 (노래 하나의 정보는 { } 묶습니다. 줄바꿈 없이) : [{'가수명', '노래제목'},{'가수명', '노래제목'}, ... ,{'가수명', '노래제목'},{'가수명', '노래제목'}]",
            tags, tags_, preferString, preferString, tags);

    public static void main(String[] args) {
        System.out.println(chatGPT(prompt));
    }
}
