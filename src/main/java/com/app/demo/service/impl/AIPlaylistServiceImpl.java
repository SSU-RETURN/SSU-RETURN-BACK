package com.app.demo.service.impl;


import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.entity.Music;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.repository.AIPlaylistMusicRepository;
import com.app.demo.repository.AIPlaylistRepository;
import com.app.demo.repository.DiaryRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.service.AIPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AIPlaylistServiceImpl implements AIPlaylistService {
    private final AIPlaylistRepository aiPlaylistRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final AIPlaylistMusicRepository aiPlaylistMusicRepository;
    @Autowired
    public AIPlaylistServiceImpl(AIPlaylistRepository aiPlaylistRepository, DiaryRepository diaryRepository, MemberRepository memberRepository, AIPlaylistMusicRepository aiPlaylistMusicRepository) {
        this.aiPlaylistRepository = aiPlaylistRepository;
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
        this.aiPlaylistMusicRepository = aiPlaylistMusicRepository;
    }

    @Override
    public List<Music> getAiPlaylist(Long diaryId){
        Diary diary = diaryRepository.findByDiaryId(diaryId);
        AIPlaylist aiPlaylist = aiPlaylistRepository.findByDiary(diary);
        List<AIPlaylistMusic> aiPlaylistMusicList = aiPlaylistMusicRepository.findByAiPlaylist(aiPlaylist);
        List<Music> aiMusicList = new ArrayList<>();
        for(AIPlaylistMusic aiPlaylistMusic:aiPlaylistMusicList){
            aiMusicList.add(aiPlaylistMusic.getMusic());
        }
        return aiMusicList;
    }


    @Override
    public AIPlaylist createAiPlaylist(Long memberId, LocalDate date) {
        Member member = memberRepository.findByMemberId(memberId);
        AIPlaylist aiPlaylist = AIPlaylist.builder()
                .member(member)
                .playlistDate(date)
                .build();
        aiPlaylistRepository.save(aiPlaylist);
        return aiPlaylist;
    }
}
