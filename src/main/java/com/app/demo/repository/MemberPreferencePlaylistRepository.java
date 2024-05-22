package com.app.demo.repository;


import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPreference;
import com.app.demo.entity.MemberPreferencePlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPreferencePlaylistRepository extends JpaRepository<MemberPreferencePlaylist, Long> {
    MemberPreferencePlaylist findByMember(Member member);
}
