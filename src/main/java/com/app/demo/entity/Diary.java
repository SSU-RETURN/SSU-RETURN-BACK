package com.app.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Diary {
    // 필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_emotion")
    private MemberEmotion memberEmotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_emotion")
    private AiEmotion aiEmotion;

    @Column(name = "picture_key", length = 200)
    private String pictureKey;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
}