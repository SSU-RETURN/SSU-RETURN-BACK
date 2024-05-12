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
@Table(name = "member_playlist")

public class MemberPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_playlist_id")
    private Long memberPlaylistId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diary")
    private Diary diary;

    @Column(name = "diary_id")
    private Long diaryId;

    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_emotion")
    private Emotion memberEmotion;

    @Column(name = "playlist_date")
    private LocalDate playlistDate;

}


