package com.app.demo.entity.mapping;

import com.app.demo.entity.MemberPreferencePlaylist;
import com.app.demo.entity.Music;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "preference_playlist_music")

public class MemberPreferencePlaylistMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_playlist_music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "preference_playlist_id")
    private MemberPreferencePlaylist memberPreferencePlaylist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "music_id")
    private Music music;

}
