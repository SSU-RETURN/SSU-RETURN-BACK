package com.app.demo.service.impl;


import com.app.demo.apiPayload.code.status.ErrorStatus;
import com.app.demo.apiPayload.exception.AuthException;
import com.app.demo.apiPayload.exception.UserException;
import com.app.demo.dto.request.MemberPreferencePlaylistRequestDTO;
import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPreference;
import com.app.demo.entity.MemberPreferencePlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.mapping.MemberPreferencePlaylistMusic;
import com.app.demo.repository.MemberPreferencePlaylistMusicRepository;
import com.app.demo.repository.MemberPreferencePlaylistRepository;
import com.app.demo.repository.MemberPreferenceRepository;
import com.app.demo.repository.MemberRepository;
import com.app.demo.service.ChatGPTService;
import com.app.demo.service.MemberPreferencePlaylistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberPreferencePlaylistServiceImpl implements MemberPreferencePlaylistService {

    private final MemberPreferencePlaylistRepository memberPreferencePlaylistRepository;
    private final MemberRepository memberRepository;
    private final MemberPreferenceRepository memberPreferenceRepository;
    private final MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository;
    private final ChatGPTService chatGPTService;


    public MemberPreferencePlaylistServiceImpl(MemberPreferencePlaylistRepository memberPreferencePlaylistRepository,
                                               MemberRepository memberRepository,
                                               MemberPreferenceRepository memberPreferenceRepository,
                                               MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository, ChatGPTService chatGPTService){
        this.memberRepository = memberRepository;
        this.memberPreferencePlaylistRepository = memberPreferencePlaylistRepository;
        this.memberPreferenceRepository = memberPreferenceRepository;
        this.memberPreferencePlaylistMusicRepository = memberPreferencePlaylistMusicRepository;
        this.chatGPTService = chatGPTService;
    }


    @Transactional
    @Override
    public MemberPreferencePlaylist createMemberPreferencePlaylist(Long memberId){
        Member member = memberRepository.findByMemberId(memberId);
        if(member == null){
            throw new UserException(ErrorStatus.USER_NOT_FOUND);
        }
        MemberPreferencePlaylist memberPreferencePlaylistPre = memberPreferencePlaylistRepository.findByMember(member);
        if(memberPreferencePlaylistPre != null){
            throw new UserException(ErrorStatus.PREFERENCE_PLAYLIST_EXISTS);
        }
        MemberPreferencePlaylist memberPreferencePlaylist = MemberPreferencePlaylist.builder()
                .member(member)
                .lastUpdateDate(LocalDate.now())
                .build();
        MemberPreference memberPreference = memberPreferenceRepository.findByMember(member);
        if (memberPreference == null) {
            throw new UserException(ErrorStatus.USER_PREFERENCE_NOT_FOUND);
        }
        List<Music> musics = getPreferenceMusicIdsByAi(memberPreference);
        for(Music music : musics){
            memberPreferencePlaylistMusicRepository.save(MemberPreferencePlaylistMusic.builder()
                    .memberPreferencePlaylist(memberPreferencePlaylist)
                    .music(music)
                    .build());
        }
        return memberPreferencePlaylistRepository.save(memberPreferencePlaylist);
    }

    @Transactional
    @Override
    public Boolean updateMemberPreferencePlaylist(MemberPreferencePlaylistRequestDTO.UpdateMemberPreferencePlaylistDTO requestDTO) {
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        if(member == null){
            throw new UserException(ErrorStatus.USER_NOT_FOUND);
        }
        MemberPreferencePlaylist memberPreferencePlaylist = memberPreferencePlaylistRepository.findByMember(member);
        if (memberPreferencePlaylist == null) {
            throw new UserException(ErrorStatus.PREFERENCE_PLAYLIST_NOT_FOUND);
        }
        if (memberPreferencePlaylist.getLastUpdateDate().isEqual(requestDTO.getDate())) {
            return Boolean.FALSE;
        } else {
            MemberPreference memberPreference = memberPreferenceRepository.findByMember(member);

            memberPreferencePlaylistMusicRepository.deleteAllByMemberPreferencePlaylist(memberPreferencePlaylist);
            List<Music> musics = getPreferenceMusicIdsByAi(memberPreference);
            for (Music music : musics) {
                memberPreferencePlaylistMusicRepository.save(MemberPreferencePlaylistMusic.builder()
                        .memberPreferencePlaylist(memberPreferencePlaylist)
                        .music(music)
                        .build());
            }
            memberPreferencePlaylist.setLastUpdateDate(requestDTO.getDate()); // Update the last update date
            memberPreferencePlaylistRepository.save(memberPreferencePlaylist); // Save the updated playlist
            return Boolean.TRUE;
        }
    }

    @Override
    public List<Music> getMemberPreferencePlaylistByMember(Long memberId){
        Member member = memberRepository.findByMemberId(memberId);
        if(member == null){
            throw new UserException(ErrorStatus.USER_NOT_FOUND);
        }
        MemberPreferencePlaylist memberPreferencePlaylist = memberPreferencePlaylistRepository.findByMember(member);
        if (memberPreferencePlaylist == null) {
            throw new UserException(ErrorStatus.PREFERENCE_PLAYLIST_NOT_FOUND);
        }
        List<MemberPreferencePlaylistMusic> memberPreferencePlaylistMusicList = memberPreferencePlaylistMusicRepository.findByMemberPreferencePlaylist(memberPreferencePlaylist);
        List<Music> musics = new ArrayList<>();
        for(MemberPreferencePlaylistMusic memberPreferencePlaylistMusic : memberPreferencePlaylistMusicList){
            musics.add(memberPreferencePlaylistMusic.getMusic());
        }
        return musics;
    }

    private List<Music> getPreferenceMusicIdsByAi(MemberPreference memberPreference){
        String genre = String.join(String.valueOf(memberPreference.getGenreFirst()),",",String.valueOf(memberPreference.getGenreSecond()), ",", String.valueOf(memberPreference.getGenreThird()));
        List<Music> musics = chatGPTService.processMusicRecommendations(genre);
        return musics;
    }
}
