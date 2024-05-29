package com.app.demo.repository;

import com.app.demo.entity.Diary;
import com.app.demo.entity.MemberPlaylist;
import com.app.demo.entity.enums.Emotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberPlaylistRepository extends JpaRepository<MemberPlaylist, Long> {

    MemberPlaylist findByMemberMemberIdAndPlaylistDate(Long memberId, LocalDate playlistDate);
    Page<MemberPlaylist> findByMemberMemberIdAndMemberEmotion(Long memberId, Emotion memberEmotion, Pageable pageable);
    List<MemberPlaylist> findByMemberMemberId(Long memberId);

    MemberPlaylist findByDiary(Diary diary);
}
