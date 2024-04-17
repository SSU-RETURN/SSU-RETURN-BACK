package com.app.demo.entity;


import com.app.demo.entity.mapping.AIPlaylistMusic;
import com.app.demo.entity.mapping.PreferencePlaylistMusic;
import com.app.demo.entity.mapping.MemberPlaylistMusic;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
