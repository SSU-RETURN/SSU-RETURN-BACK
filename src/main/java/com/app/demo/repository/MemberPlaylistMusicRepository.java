package com.app.demo.repository;

import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MemberPlaylistMusicRepository extends JpaRepository<MemberPlaylistMusic, Long> {
    List<MemberPlaylistMusic> findByMemberPlaylistMemberPlaylistId(Long memberPlaylistId);
}
