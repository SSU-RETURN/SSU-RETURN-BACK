package com.app.demo.dto.request;

import com.app.demo.entity.enums.Genre;
import com.app.demo.entity.enums.Preference;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class MemberPreferenceRequestDTO {



    @Data
    @Builder
    public static class CreateMemberPreferenceRequestDTO{
        private Long memberId;
        private Genre genreFirst;
        private Genre genreSecond;
        private Genre genreThird;
        private Preference preferenceSad;
        private Preference preferenceHappy;
        private Preference preferenceAngry;
        private Preference preferenceRomance;
        private Preference preferenceSurprise;
    }


    @Data
    @Builder
    public static class UpdateMemberPreferenceRequestDTO{
        private Long memberId;
        private Genre genreFirst;
        private Genre genreSecond;
        private Genre genreThird;
        private Preference preferenceSad;
        private Preference preferenceHappy;
        private Preference preferenceAngry;
        private Preference preferenceRomance;
        private Preference preferenceSurprise;
    }


}
