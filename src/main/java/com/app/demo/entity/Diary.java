package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "Diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "member_id", nullable = false)
    private User userId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_emotion")
    private Emotion userEmotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_emotion")
    private Emotion aiEmotion;

    @Column(name = "picture_key", length = 200, nullable = true)
    private String pictureKey;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "diary")
    private List<AIPlaylist> aiPlaylistList = new ArrayList<>();

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "diary")
    private List<UserPlaylist> userPlaylistList = new ArrayList<>();
}