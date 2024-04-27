package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ai_playlist")

public class AIPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_Playlist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", updatable = false)
    private Diary diary;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_emotion")
    private Emotion aiEmotion;

    @Column(name = "playlist_date")
    private LocalDateTime playlistDate;

    @PostLoad
    private void loadAiEmotion() {
        if (diary != null) {
            aiEmotion = diary.getAiEmotion();
        }
    }

}


