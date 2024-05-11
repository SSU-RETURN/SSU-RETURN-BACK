package com.app.demo.service;

import com.app.demo.entity.AIPlaylist;

public interface AIPlaylistService {
    AIPlaylist getAIPlaylistByDiaryId(Long diaryId);
}
