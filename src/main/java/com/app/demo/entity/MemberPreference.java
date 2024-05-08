package com.app.demo.entity;

import com.app.demo.entity.enums.Genre;
import com.app.demo.entity.enums.Preference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memberPreference")
public class MemberPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberPreference_id")
    private Long memberPreferenceId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_first")
    private Genre genreFirst;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_second")
    private Genre genreSecond;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre_third")
    private Genre genreThird;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_Sad")
    private Preference preferenceSad;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_Happy")
    private Preference preferenceHappy;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_Angry")
    private Preference preferenceAngry;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_Romance")
    private Preference preferenceRomance;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefer_Anxious")
    private Preference preferenceAnxious;

}
