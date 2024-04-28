package com.app.demo.repository;

import com.app.demo.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByMemberId(Long memberId);
}
