package com.app.demo.repository;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIPlaylistMusicRepository extends JpaRepository<AIPlaylistMusic, Long> {
    List<AIPlaylistMusic> findByAiPlaylist(AIPlaylist aiPlaylist);
}
