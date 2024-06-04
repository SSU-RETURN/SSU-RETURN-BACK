package com.app.demo.converter;

import com.app.demo.dto.response.RecommendPageResponseDTO;
import com.app.demo.entity.Music;

import java.util.ArrayList;
import java.util.List;

public class RecommendPageConverter {
    public static RecommendPageResponseDTO.RecommendPageDTO toRecommendPageDTO(List<Music> aiPlaylist, List<Music> memberEmotionPlaylist, Long diaryId){
        return RecommendPageResponseDTO.RecommendPageDTO.builder()
                .aiPlaylist(aiPlaylist)
                .memberEmotionPlaylist(memberEmotionPlaylist)
                .diaryId(diaryId)
                .build();
    }
    public static RecommendPageResponseDTO.RecommendPageDTO toNullDTO(){
        List<Music> aiPlaylist = new ArrayList<>();
        List<Music> memberEmotionPlaylist = new ArrayList<>();
        return RecommendPageResponseDTO.RecommendPageDTO.builder()
                .memberEmotionPlaylist(memberEmotionPlaylist)
                .aiPlaylist(aiPlaylist)
                .build();
    }
}
