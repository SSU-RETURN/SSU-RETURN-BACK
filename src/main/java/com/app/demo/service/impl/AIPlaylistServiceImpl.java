package com.app.demo.service.impl;


import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.repository.AIPlaylistRepository;
import com.app.demo.repository.DiaryRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.service.AIPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AIPlaylistServiceImpl implements AIPlaylistService {
    private final AIPlaylistRepository aiPlaylistRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    @Autowired
    public AIPlaylistServiceImpl(AIPlaylistRepository aiPlaylistRepository, DiaryRepository diaryRepository, MemberRepository memberRepository) {
        this.aiPlaylistRepository = aiPlaylistRepository;
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public AIPlaylist getAiPlaylist(Long diaryId){
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        return aiPlaylistRepository.findByDiary(diary);
    }
    

    @Override
    public AIPlaylist createAiPlaylist(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        AIPlaylist aiPlaylist = AIPlaylist.builder().member(member).build();
        aiPlaylistRepository.save(aiPlaylist);
        return aiPlaylist;
    }
}
