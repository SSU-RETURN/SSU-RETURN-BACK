package com.app.demo.repository;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Diary;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AIPlaylistRepository extends JpaRepository<AIPlaylist, Long> {
    AIPlaylist findByDiary(Optional<Diary> diary);
}
