package com.app.demo.service;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.entity.Music;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AIPlaylistService {
    AIPlaylist createAiPlaylist(Long memberId, LocalDate date);
    List<Music> getAiPlaylist(Long diaryId);
}
