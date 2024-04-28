package com.app.demo.repository;

import com.app.demo.entity.Diary;
import com.app.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByMemberIdAndWrittenDateBetween(Long memberId, LocalDate startDate, LocalDate endDate);


}