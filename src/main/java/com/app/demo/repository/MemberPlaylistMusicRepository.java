package com.app.demo.repository;

import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MemberPlaylistMusicRepository extends JpaRepository<MemberPlaylistMusic, Long> {
    Page<MemberPlaylistMusic> findByMemberPlaylistMemberPlaylistId(Long memberPlaylistId, Pageable pageable);
    List<MemberPlaylistMusic> findByMemberPlaylistMemberPlaylistId(Long memberPlaylistId);
    void deleteAllByMemberPlaylist(MemberPlaylist memberPlaylist);
}
