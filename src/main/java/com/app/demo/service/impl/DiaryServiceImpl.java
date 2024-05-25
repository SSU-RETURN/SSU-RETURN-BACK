package com.app.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.*;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.enums.Preference;
import com.app.demo.repository.*;
import com.app.demo.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final AIPlaylistRepository aiPlaylistRepository;
    private final MemberPlaylistRepository memberPlaylistRepository;
    private final MusicRepository musicRepository;
    private AIPlaylistService aiPlaylistService;
    private AiEmotionService aiEmotionService;
    private AiPlaylistMusicService aiPlaylistMusicService;
    private ChatGPTService chatGPTService;
    private MemberPreferenceService memberPreferenceService;
    private MemberPlaylistService memberPlaylistService;
    private MemberPlaylistMusicService memberPlaylistMusicService;

    @Autowired
    private AmazonS3 s3Client;

    private final String bucketName = System.getenv("BUCKET_NAME");

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, MemberRepository memberRepository,
                            AIPlaylistRepository aiPlaylistRepository, MemberPlaylistRepository memberPlaylistRepository, MusicRepository musicRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
        this.aiPlaylistRepository = aiPlaylistRepository;
        this.memberPlaylistRepository = memberPlaylistRepository;
        this.musicRepository = musicRepository;
    }

    @Override
    public Diary createDiary(DiaryRequestDTO.CreateDiaryRequestDTO requestDTO) {
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        List<Long> MembermusicList = requestDTO.getMusicList();

        //Bert
        AiEmotion aiEmotion = aiEmotionService.getAiEmotion(requestDTO.getContent());
        //chatGPT + 추천음악 저장
        String preference = String.valueOf(memberPreferenceService.getMemberPreferenceForGPT(member, String.valueOf(requestDTO.getMemberEmotion())));
        MemberPreference memberPreference = memberPreferenceService.getMemberPreferenceByMemberId(member.getMemberId());
        String genre = String.join(String.valueOf(memberPreference.getGenreFirst()),",",String.valueOf(memberPreference.getGenreSecond()));
        List<Music> musicList= chatGPTService.processMusicRecommendations(String.valueOf(requestDTO.getMemberEmotion()), preference, genre);
        //aiPlaylist 저장
        AIPlaylist aiPlaylist = aiPlaylistService.createAiPlaylist(member.getMemberId(), requestDTO.getWrittenDate());
        aiPlaylistMusicService.setAiPlaylistMusic(musicList, aiPlaylist);
        //memberMusic 변환
        List<Music> musics= musicRepository.findByIdIn(MembermusicList);
        //memberPlaylist 저장
        MemberPlaylist memberPlaylist = memberPlaylistService.createMemberPlaylist(member, requestDTO.getMemberEmotion(), requestDTO.getWrittenDate());
        memberPlaylistMusicService.setMemberPlaylistMusic(musics, memberPlaylist);
        //s3 저장
        String imageUrl = "";
        if (requestDTO.getPictureKey() != null && !requestDTO.getPictureKey().isEmpty()) {
            imageUrl = uploadFileToS3(requestDTO.getPictureKey());
        }

        //다이어리 저장
        Diary diary = Diary.builder()
                .content(requestDTO.getContent())
                .memberEmotion(requestDTO.getMemberEmotion())
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

    private String uploadFileToS3(MultipartFile file) {
        String fileName = generateFileName(file);
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Error in storing file to S3", e);
        }
    }

    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + file.getOriginalFilename().replace(" ", "_");
    }

    @Override
    public Diary updateDiary(DiaryRequestDTO.UpdateDiaryRequestDTO requestDTO) {
        Diary diary = diaryRepository.findById(requestDTO.getDiaryId()).orElse(null);
        if (diary == null) {
            return null;
        }
        if (requestDTO.getContent() != null) {
            diary.setContent(requestDTO.getContent());
        }
        if (requestDTO.getPictureKey() != null) {
            diary.setPictureKey(requestDTO.getPictureKey());
        }
        return diaryRepository.save(diary);
    }

    @Override
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    public List<Diary> getDiariesByMonth(Long memberId, LocalDate yearMonth) {
        LocalDate startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return diaryRepository.findByMemberIdAndWrittenDateBetween(memberId, startDate, endDate);
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
}


