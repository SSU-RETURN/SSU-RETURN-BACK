package com.app.demo.service.impl;


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
import com.app.demo.service.MemberPreferencePlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberPreferencePlaylistServiceImpl implements MemberPreferencePlaylistService {



    private final MemberPreferencePlaylistRepository memberPreferencePlaylistRepository;
    private final MemberRepository memberRepository;
    private final MemberPreferenceRepository memberPreferenceRepository;
    private final MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository;

    public MemberPreferencePlaylistServiceImpl(MemberPreferencePlaylistRepository memberPreferencePlaylistRepository,
                                               MemberRepository memberRepository,
                                               MemberPreferenceRepository memberPreferenceRepository,
                                               MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository){
        this.memberRepository = memberRepository;
        this.memberPreferencePlaylistRepository = memberPreferencePlaylistRepository;
        this.memberPreferenceRepository = memberPreferenceRepository;
        this.memberPreferencePlaylistMusicRepository = memberPreferencePlaylistMusicRepository;
    }

    @Override
    public MemberPreferencePlaylist createMemberPreferencePlaylist(MemberPreferencePlaylistRequestDTO.CreateMemberPreferencePlaylistDTO requestDTO){
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        MemberPreferencePlaylist memberPreferencePlaylist = MemberPreferencePlaylist.builder()
                .member(member)
                .lastUpdateDate(LocalDate.now())
                .build();
        //1. 취향을 AI에 집어넣어서 음악 정보를 얻고(AI가 주어진 Preference를 기반으로 음악을 검색하고, <제목, 가수>형태의 값들을 얻는다.
        //   <제목, 가수>형태의 값들을 music관련 함수를 사용해서 List<Music>을 얻는다
        //2. music과 Playlist를 매핑시켜서 매핑객체들을 만들기

        MemberPreference memberPreference = memberPreferenceRepository.findByMember(member);
        List<Music> musics = getPreferenceMusicIdsByAi(memberPreference);
        for(Music music : musics){
            memberPreferencePlaylistMusicRepository.save(MemberPreferencePlaylistMusic.builder()
                    .memberPreferencePlaylist(memberPreferencePlaylist)
                    .music(music)
                    .build());
        }
        return memberPreferencePlaylistRepository.save(memberPreferencePlaylist);
    }

    @Override
    public Boolean updateMemberPreferencePlaylist(MemberPreferencePlaylistRequestDTO.UpdateMemberPreferencePlaylistDTO requestDTO){
        Member member = memberRepository.findByMemberId(requestDTO.getMemberId());
        MemberPreferencePlaylist memberPreferencePlaylist = memberPreferencePlaylistRepository.findByMember(member);
        if(memberPreferencePlaylist.getLastUpdateDate().isEqual(requestDTO.getDate())){
            return Boolean.FALSE;
        }else{
            MemberPreference memberPreference = memberPreferenceRepository.findByMember(member);
            memberPreferencePlaylistMusicRepository.deleteAllByMemberPreferencePlaylist(memberPreferencePlaylist);
            List<Music> musics = getPreferenceMusicIdsByAi(memberPreference);
            for(Music music : musics){
                memberPreferencePlaylistMusicRepository.save(MemberPreferencePlaylistMusic.builder()
                        .memberPreferencePlaylist(memberPreferencePlaylist)
                        .music(music)
                        .build());
            }
            return Boolean.TRUE;
        }
    }

    @Override
    public MemberPreferencePlaylist getMemberPreferencePlaylistByMember(Long memberId){
        Member member = memberRepository.findByMemberId(memberId);
        return memberPreferencePlaylistRepository.findByMember(member);
    }

    private List<Music> getPreferenceMusicIdsByAi(MemberPreference memberPreference){
        //Preference를 Ai에 넣어 <제목, 가수> 획득
        //<제목,가수>를 music API에 넣어 List<Music> musics 획득
        List<Music> musics = new ArrayList<>();
        return musics;
    }
}
