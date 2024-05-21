package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member")
    private Member member;

    @Column(name = "member_id")
    private Long memberId;

    @Column(length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_emotion")
    private Emotion memberEmotion;

    @OneToOne(mappedBy = "diary", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private AiEmotion aiEmotion;

    @Column(name = "picture_key", length = 200, nullable = true)
    private String pictureKey;

    @Column(name = "written_date")
    private LocalDate writtenDate;

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "ai_playlist")
    private AIPlaylist aiPlaylist;

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_playlist")
    private MemberPlaylist memberPlaylist;

}