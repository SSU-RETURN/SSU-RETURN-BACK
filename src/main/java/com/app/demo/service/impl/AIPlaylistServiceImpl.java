package com.app.demo.service.impl;


import com.app.demo.entity.AIPlaylist;
import com.app.demo.repository.AIPlaylistRepository;
import com.app.demo.service.AIPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIPlaylistServiceImpl implements AIPlaylistService {
    private final AIPlaylistRepository aiPlaylistRepository;

    @Autowired
    public AIPlaylistServiceImpl(AIPlaylistRepository aiPlaylistRepository) {
        this.aiPlaylistRepository = aiPlaylistRepository;
    }

    @Override
    public AIPlaylist getAIPlaylistByDiaryId(Long diaryId) {
        return aiPlaylistRepository.findByDiaryId(diaryId);
    }
}
