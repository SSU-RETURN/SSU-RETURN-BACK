package com.app.demo.converter;

import com.app.demo.entity.MemberPreferencePlaylist;
import com.app.demo.entity.Music;
import com.app.demo.repository.MemberPreferencePlaylistMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberPreferencePlaylistMusicsConverter {

    @Autowired
    public final MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository;

    public MemberPreferencePlaylistMusicsConverter(MemberPreferencePlaylistMusicRepository memberPreferencePlaylistMusicRepository) {
        this.memberPreferencePlaylistMusicRepository = memberPreferencePlaylistMusicRepository;
    }

    public List<Music> toMemberPreferencePlaylistMusics(MemberPreferencePlaylist memberPreferencePlaylist){
        return memberPreferencePlaylistMusicRepository.findMusicByMemberPreferencePlaylist(memberPreferencePlaylist);
    }
}
