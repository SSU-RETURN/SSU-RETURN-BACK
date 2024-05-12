package com.app.demo.repository;

import com.app.demo.entity.mapping.MemberPlaylistMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MemberPlaylistMusicRepository extends JpaRepository<MemberPlaylistMusic, Long> {
    List<MemberPlaylistMusic> findByMemberPlaylistId(Long memberPlaylistId);
}
