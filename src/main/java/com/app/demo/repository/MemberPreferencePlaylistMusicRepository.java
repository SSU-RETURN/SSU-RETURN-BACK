package com.app.demo.repository;


import com.app.demo.entity.MemberPreferencePlaylist;
import com.app.demo.entity.Music;
import com.app.demo.entity.mapping.MemberPreferencePlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberPreferencePlaylistMusicRepository extends JpaRepository<MemberPreferencePlaylistMusic, Long> {
    void deleteAllByMemberPreferencePlaylist(MemberPreferencePlaylist memberPreferencePlaylist);
    List<Music> findMusicByMemberPreferencePlaylist(MemberPreferencePlaylist memberPreferencePlaylist);
}
