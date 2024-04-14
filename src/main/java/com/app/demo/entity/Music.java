package com.app.demo.entity;


import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.entity.mapping.PreferencePlaylistMusic;
import com.app.demo.entity.mapping.UserPlaylistMusic;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Entity
@Table(name = "music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String artist;

    @Column(name = "picture_key", length = 200, nullable = false)
    private String picture_key;


    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "music")
    private final List<AIPlaylistMusic> aiPlaylistMusicList = new ArrayList<>();

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "music")
    private final List<UserPlaylistMusic> userPlaylistMusicList = new ArrayList<>();

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "music")
    private final List<PreferencePlaylistMusic> preferencePlaylistMusicList = new ArrayList<>();



}
