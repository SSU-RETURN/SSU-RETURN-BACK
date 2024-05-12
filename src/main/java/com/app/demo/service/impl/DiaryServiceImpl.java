package com.app.demo.service.impl;

import com.app.demo.dto.request.DiaryRequestDTO;
import com.app.demo.dto.response.DiaryResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.service.DiaryService;
import com.app.demo.repository.DiaryRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.repository.AIPlaylistRepository;
import com.app.demo.repository.MemberPlaylistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final AIPlaylistRepository aiPlaylistRepository;
    private final MemberPlaylistRepository memberPlaylistRepository;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, MemberRepository memberRepository,
                            AIPlaylistRepository aiPlaylistRepository, MemberPlaylistRepository memberPlaylistRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
        this.aiPlaylistRepository = aiPlaylistRepository;
        this.memberPlaylistRepository = memberPlaylistRepository;
    }

    @Override
    public Diary createDiary(DiaryRequestDTO.CreateDiaryRequestDTO requestDTO) {
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        Emotion aiEmotion = extractAiEmotion(requestDTO.getContent());
        //List<Long> musicList = requestDTO.getMusicList;

        AIPlaylist aiPlaylist = AIPlaylist.builder()
                .memberId(requestDTO.getMemberId())
                .aiEmotion(aiEmotion)
                .build();
        MemberPlaylist memberPlaylist = MemberPlaylist.builder()
                .memberId(requestDTO.getMemberId())
                .memberEmotion(requestDTO.getMemberEmotion())
                .build();
        //.musicList(musicList)
        Diary diary = Diary.builder()
                .member(member)
                .memberId(requestDTO.getMemberId())
                .content(requestDTO.getContent())
                .memberEmotion(requestDTO.getMemberEmotion())
                .aiEmotion(aiEmotion)
                .pictureKey(requestDTO.getPictureKey())
                .writtenDate(requestDTO.getWrittenDate())
                .aiPlaylist(aiPlaylist)
                .memberPlaylist(memberPlaylist)
                .build();

        diaryRepository.save(diary);
        memberPlaylist.setDiary(diary);
        memberPlaylist.setPlaylistDate(diary.getWrittenDate());
        memberPlaylist.setDiaryId(diary.getDiaryId());
        aiPlaylist.setDiary(diary);
        aiPlaylist.setPlaylistDate(diary.getWrittenDate());
        aiPlaylist.setDiaryId(diary.getDiaryId());
        memberPlaylistRepository.save(memberPlaylist);
        aiPlaylistRepository.save(aiPlaylist);

        return diary;

    }

    private Emotion extractAiEmotion(String content) {
        return Emotion.HAPPY;       //더미값
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
}


