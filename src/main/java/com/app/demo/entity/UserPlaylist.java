package com.app.demo.entity;

import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.mapping.UserPlaylistMusic;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "userPlaylist")

public class UserPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userPlaylist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", updatable = false)
    private Diary diary;

    @Column(name = "user_emotion")
    private Emotion userEmotion;

    @PostLoad
    private void loadMemberEmotion() {
        if (diary != null) {
            userEmotion = diary.getUserEmotion();
        }
    }

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userPlaylist")
    private List<UserPlaylistMusic> userPlaylistMusicList;
}


