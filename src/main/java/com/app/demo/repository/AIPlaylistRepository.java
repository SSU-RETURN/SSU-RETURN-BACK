package com.app.demo.repository;

import com.app.demo.entity.AIPlaylist;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AIPlaylistRepository extends JpaRepository<AIPlaylist, Long> {
    AIPlaylist findByDiaryId(Long diaryId);
}
