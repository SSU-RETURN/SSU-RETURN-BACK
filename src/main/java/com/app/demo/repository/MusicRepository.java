package com.app.demo.repository;

import com.app.demo.entity.Music;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository <Music, Long>{
    Optional<Music> findByTitle(String title);
    Optional<Music> findByArtist(String artist);

}
