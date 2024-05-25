package com.app.demo.repository;


import com.app.demo.entity.Member;
import com.app.demo.entity.MemberPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPreferenceRepository extends JpaRepository<MemberPreference, Long> {
    MemberPreference findByMember(Member member);
    void deleteByMember(Member member);
}
