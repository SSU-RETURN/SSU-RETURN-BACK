package com.app.demo.service;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;

import java.util.Optional;

public interface AIPlaylistService {
    AIPlaylist createAiPlaylist(Long memberId);
    AIPlaylist getAiPlaylist(Long diaryId);
}
