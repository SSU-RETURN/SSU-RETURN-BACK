package com.app.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "AiEmotion")
public class AiEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sad;

    private Integer happy;

    private Integer angry;

    private Integer neutral;

    private Integer surprise;

    private Integer anxious;

}