package com.app.demo.converter;

import com.app.demo.dto.response.MemberPlaylistResponseDTO;
import com.app.demo.entity.Diary;
import com.app.demo.entity.MemberPlaylist;

import com.app.demo.entity.Music;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import com.app.demo.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.demo.repository.MemberPlaylistMusicRepository;

import java.util.ArrayList;
import java.util.List;

public class MemberPlaylistConverter {

    private final MemberPlaylistMusicRepository memberPlaylistMusicRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public MemberPlaylistConverter(MusicRepository musicRepository, MemberPlaylistMusicRepository memberPlaylistMusicRepository)
    {
        this.memberPlaylistMusicRepository = memberPlaylistMusicRepository;
        this.musicRepository = musicRepository;
    }

    public MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO toMemberPlaylistMusics(MemberPlaylist memberPlaylist){
        Long memberPlaylistId = memberPlaylist.getMemberPlaylistId();
        List<MemberPlaylistMusic> memberPlaylistMusicList = memberPlaylistMusicRepository.findByMemberPlaylistId(memberPlaylistId);
        List<Music> memberPlaylistMusics = new ArrayList<>();
        for(MemberPlaylistMusic memberPlaylistMusic : memberPlaylistMusicList){
            Long musicId = memberPlaylistMusic.getId();
            musicRepository.findById(musicId).ifPresent(memberPlaylistMusics::add);
        }
        return MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO.builder()
                .memberPlaylistMusics(memberPlaylistMusics)
                .build();

    }

    public MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO toMemberPlaylistListMusics(List<MemberPlaylist> memberPlaylistList){
        List<Long> memberPlaylistId = new ArrayList<>();
        for(MemberPlaylist memberPlaylist : memberPlaylistList){
            Long id = memberPlaylist.getMemberPlaylistId();
            memberPlaylistId.add(id);
        }
        List<MemberPlaylistMusic> memberPlaylistMusicList = new ArrayList<>();
        for (Long id : memberPlaylistId) {
            memberPlaylistMusicList.addAll(memberPlaylistMusicRepository.findByMemberPlaylistId(id));
        }
        List<Music> memberPlaylistMusics = new ArrayList<>();
        for(MemberPlaylistMusic memberPlaylistMusic : memberPlaylistMusicList){
            Long musicId = memberPlaylistMusic.getId();
            musicRepository.findById(musicId).ifPresent(memberPlaylistMusics::add);
        }
        return MemberPlaylistResponseDTO.MemberPlaylistMusicsDTO.builder()
                .memberPlaylistMusics(memberPlaylistMusics)
                .build();
    }

    public MemberPlaylistResponseDTO.TestDTO toTestDateDTO(MemberPlaylist memberPlaylist){
        return MemberPlaylistResponseDTO.TestDTO.builder()
                .playlistId(memberPlaylist.getMemberPlaylistId())
                .memberId(memberPlaylist.getMember().getMemberId())
                .diaryId(memberPlaylist.getDiary().getDiaryId())
                .pictureKey(memberPlaylist.getDiary().getPictureKey())
                //.diary(memberPlaylist.getDiary())
                .build();
    }

    public List<MemberPlaylistResponseDTO.TestDTO> toTestEmoDTO(List<MemberPlaylist> memberPlaylistList){
        List<MemberPlaylistResponseDTO.TestDTO> testDTOS = new ArrayList<>();
        for(MemberPlaylist memberPlaylist: memberPlaylistList){
            testDTOS.add(MemberPlaylistResponseDTO.TestDTO.builder()
                    .playlistId(memberPlaylist.getMemberPlaylistId())
                    .memberId(memberPlaylist.getMember().getMemberId())
                    .diaryId(memberPlaylist.getDiary().getDiaryId())
                    .pictureKey(memberPlaylist.getDiary().getPictureKey())
                    .build());
        }
        return testDTOS;
    }
}
