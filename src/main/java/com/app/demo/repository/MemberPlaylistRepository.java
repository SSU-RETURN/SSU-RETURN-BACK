package com.app.demo.repository;

import com.app.demo.entity.MemberPlaylist;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MemberPlaylistRepository extends JpaRepository<MemberPlaylist, Long> {
}
