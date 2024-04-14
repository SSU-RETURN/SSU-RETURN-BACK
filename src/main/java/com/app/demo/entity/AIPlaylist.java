package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.AIPlaylistMusic;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "aiPlaylist")

public class AIPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aiPlaylist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", updatable = false)
    private Diary diary;

    @Column(name = "ai_emotion")
    private Emotion aiEmotion;

    @PostLoad
    private void loadAiEmotion() {
        if (diary != null) {
            aiEmotion = diary.getAiEmotion();
        }
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "aiPlaylist")
    private List<AIPlaylistMusic> aiPlaylistMusicList;
}


