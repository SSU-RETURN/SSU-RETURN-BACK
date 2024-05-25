package com.app.demo.service;

import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.Music;

import java.util.List;

public interface MemberPlaylistMusicService {
    void setMemberPlaylistMusic(List<Music> musics, MemberPlaylist memberPlaylist);
}
