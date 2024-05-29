package com.app.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.entity.*;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.repository.*;
import com.app.demo.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final AIPlaylistRepository aiPlaylistRepository;
    private final MemberPlaylistRepository memberPlaylistRepository;
    private final MusicRepository musicRepository;
    private final AIPlaylistService aiPlaylistService;
    private final AiEmotionService aiEmotionService;
    private final AiPlaylistMusicService aiPlaylistMusicService;
    private final ChatGPTService chatGPTService;
    private final MemberPreferenceService memberPreferenceService;
    private final MemberPlaylistService memberPlaylistService;
    private final MemberPlaylistMusicService memberPlaylistMusicService;
    private final AIPlaylistMusicRepository aiPlaylistMusicRepository;
    private final MemberPlaylistMusicRepository memberPlaylistMusicRepository;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, MemberRepository memberRepository,
                            AIPlaylistRepository aiPlaylistRepository, MemberPlaylistRepository memberPlaylistRepository, MusicRepository musicRepository, AIPlaylistService aiPlaylistService, AiEmotionService aiEmotionService, AiPlaylistMusicService aiPlaylistMusicService, ChatGPTService chatGPTService, MemberPreferenceService memberPreferenceService, MemberPlaylistService memberPlaylistService, MemberPlaylistMusicService memberPlaylistMusicService, S3ImageService s3ImageService, AIPlaylistMusicRepository aiPlaylistMusicRepository, MemberPlaylistMusicRepository memberPlaylistMusicRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
        this.aiPlaylistRepository = aiPlaylistRepository;
        this.memberPlaylistRepository = memberPlaylistRepository;
        this.musicRepository = musicRepository;
        this.aiPlaylistService = aiPlaylistService;
        this.aiEmotionService = aiEmotionService;
        this.aiPlaylistMusicService = aiPlaylistMusicService;
        this.chatGPTService = chatGPTService;
        this.memberPreferenceService = memberPreferenceService;
        this.memberPlaylistService = memberPlaylistService;
        this.memberPlaylistMusicService = memberPlaylistMusicService;
        this.aiPlaylistMusicRepository = aiPlaylistMusicRepository;
        this.memberPlaylistMusicRepository = memberPlaylistMusicRepository;
    }

    @Override
    public Diary createDiary(DiaryRequestDTO.CreateDiaryRequestDTO requestDTO, String imageUrl) {
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        List<Long> MembermusicList = requestDTO.getMusicList();

        //Bert
        AiEmotion aiEmotion = aiEmotionService.getAiEmotion(requestDTO.getContent());
        //chatGPT + 추천음악 저장
        String preference = String.valueOf(memberPreferenceService.getMemberPreferenceForGPT(member, requestDTO.getMemberEmotion()));
        MemberPreference memberPreference = memberPreferenceService.getMemberPreferenceByMemberId(member.getMemberId());
        String genre = String.join(String.valueOf(memberPreference.getGenreFirst()),",",String.valueOf(memberPreference.getGenreSecond()));
        List<Music> musicList= chatGPTService.processMusicRecommendations(String.valueOf(requestDTO.getMemberEmotion()), preference, genre);
        //aiPlaylist 저장
        AIPlaylist aiPlaylist = aiPlaylistService.createAiPlaylist(member.getMemberId(), requestDTO.getWrittenDate());
        aiPlaylistMusicService.setAiPlaylistMusic(musicList, aiPlaylist);
        //memberMusic 변환
        List<Music> musics= musicRepository.findByIdIn(MembermusicList);
        //memberPlaylist 저장
        MemberPlaylist memberPlaylist = memberPlaylistService.createMemberPlaylist(member, Emotion.valueOf(requestDTO.getMemberEmotion()), requestDTO.getWrittenDate());
        memberPlaylistMusicService.setMemberPlaylistMusic(musics, memberPlaylist);

        //다이어리 저장
        Diary diary = Diary.builder()
                .member(member)
                .content(requestDTO.getContent())
                .memberEmotion(Emotion.valueOf(requestDTO.getMemberEmotion()))
                .aiEmotion(aiEmotion)
                .pictureKey(imageUrl)
                .writtenDate(requestDTO.getWrittenDate())
                .aiPlaylist(aiPlaylist)
                .memberPlaylist(memberPlaylist)
                .build();

        diaryRepository.save(diary);
        aiPlaylist.setDiary(diary);
        memberPlaylist.setDiary(diary);

        return diary;

    }

    @Override
    public void updateDiary(DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO) {
        Diary diary = diaryRepository.findById(requestDTO.getDiaryId()).orElse(null);
        if (diary == null) {
            return;
        }
        if (requestDTO.getContent() != null) {
            diary.setContent(requestDTO.getContent());
        }
        if (requestDTO.getPictureKey() != null) {
            diary.setPictureKey(requestDTO.getPictureKey());
        }
        diaryRepository.save(diary);
    }


    @Transactional
    @Override
    public void deleteDiary(Long diaryId) {
        Diary diary = diaryRepository.findByDiaryId(diaryId);

        AIPlaylist aiPlaylist = aiPlaylistRepository.findByDiary(diary);
        aiPlaylistMusicRepository.deleteAllByAiPlaylist(aiPlaylist);
        aiPlaylist.setMember(null);


        MemberPlaylist memberPlaylist = memberPlaylistRepository.findByDiary(diary);
        memberPlaylistMusicRepository.deleteAllByMemberPlaylist(memberPlaylist);
        memberPlaylist.setMember(null);

        diary.setMember(null);

        aiPlaylistRepository.delete(aiPlaylist);
        memberPlaylistRepository.delete(memberPlaylist);


        diaryRepository.delete(diary);

    }

    public List<Diary> getDiariesByMonth(Long memberId, LocalDate yearMonth) {
        Member member = memberRepository.findByMemberId(memberId);
        LocalDate startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return diaryRepository.findByMemberAndWrittenDateBetween(member, startDate, endDate);
    }



    public Diary getDiary(Long diaryId) {
        return diaryRepository.findByDiaryId(diaryId);
    }

    @Override
    public AiEmotion getAiEmotionFromDiary(Long diaryId) {
        Diary diary = diaryRepository.findByDiaryId(diaryId);
        AiEmotion aiEmotion = diary.getAiEmotion();
        return aiEmotion;
    }
    @Override
    public Diary getDiaryByMemberDate(Long memberId, LocalDate date){
        Member member = memberRepository.findByMemberId(memberId);
        return diaryRepository.findByMemberAndWrittenDate(member, date);
    }
}


