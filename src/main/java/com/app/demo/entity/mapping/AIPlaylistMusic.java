package com.app.demo.entity.mapping;

import com.app.demo.entity.AIPlaylist;
import com.app.demo.entity.Member;
import com.app.demo.entity.Music;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ai_playlist_music")

public class AIPlaylistMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_mappingtable_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_playlist")
    private AIPlaylist aiPlaylist;

    @Column(name = "ai_playlist_id")
    private Long aiPlaylistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

}
