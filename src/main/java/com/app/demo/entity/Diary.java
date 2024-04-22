package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_emotion")
    private Emotion memberEmotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_emotion")
    private Emotion aiEmotion;

    @Column(name = "picture_key", length = 200, nullable = true)
    private String pictureKey;

    @Column(name = "written_date")
    private LocalDate writtenDate;

    @OneToOne
    @Column(name = "ai_playlist")
    private AIPlaylist aiPlaylist;

    @OneToOne
    @JoinColumn(name = "member_playlist")
    private MemberPlaylist memberPlaylist;

}