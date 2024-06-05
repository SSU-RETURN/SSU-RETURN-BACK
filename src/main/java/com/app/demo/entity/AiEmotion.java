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
    private Integer surprise;

    public void EmotionTracker(Integer sad, Integer happy, Integer angry, Integer surprise) {
        this.sad = sad;
        this.happy = happy;
        this.angry = angry;
        this.surprise = surprise;
    }

    public String findDominantEmotion() {
        Integer maxEmotionValue = Math.max(Math.max(sad, happy), Math.max(angry, surprise));
        if (maxEmotionValue.equals(sad)) {
            return "Sad";
        } else if (maxEmotionValue.equals(happy)) {
            return "Happy";
        } else if (maxEmotionValue.equals(angry)) {
            return "Angry";
        } else {
            return "Surprised";
        }
    }

}

