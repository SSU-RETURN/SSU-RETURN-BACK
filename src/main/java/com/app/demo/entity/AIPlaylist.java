package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private Long aiPlaylistId;

    @Column(name = "member_id")
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary")
    private Diary diary;

    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "ai_emotion")
    private List<Float> aiEmotion;

    @Column(name = "playlist_date")
    private LocalDate playlistDate;

}


