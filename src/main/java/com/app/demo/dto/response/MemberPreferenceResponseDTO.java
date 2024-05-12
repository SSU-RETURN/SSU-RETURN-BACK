package com.app.demo.dto.response;

import com.app.demo.entity.Member;
import com.app.demo.entity.enums.Emotion;
import com.app.demo.entity.enums.Genre;
import com.app.demo.entity.enums.Preference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MemberPreferenceResponseDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberPreferenceContentDTO {
        private Genre genreFirst;
        private Genre genreSecond;
        private Genre genreThird;
        private Preference preferenceSad;
        private Preference preferenceHappy;
        private Preference preferenceAngry;
        private Preference preferenceRomance;
        private Preference preferenceAnxious;
    }
}
