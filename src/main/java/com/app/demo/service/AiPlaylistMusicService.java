package com.app.demo.service;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Member;
import com.app.demo.entity.Music;
import com.app.demo.entity.enums.Preference;

import java.util.List;

public interface AiPlaylistMusicService {
    void setAiPlaylistMusic(List<Music> musics, AIPlaylist aiPlaylist);
}
