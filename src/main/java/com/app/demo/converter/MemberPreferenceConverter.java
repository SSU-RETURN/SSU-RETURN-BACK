package com.app.demo.converter;

import com.app.demo.dto.response.MemberPreferenceResponseDTO;
import com.app.demo.entity.MemberPreference;

public class MemberPreferenceConverter {

    public static MemberPreferenceResponseDTO.MemberPreferenceContentDTO toMemberPreferenceContentDTO(MemberPreference memberPreference){
        return MemberPreferenceResponseDTO.MemberPreferenceContentDTO.builder()
                .genreFirst(memberPreference.getGenreFirst())
                .genreSecond(memberPreference.getGenreSecond())
                .genreThird(memberPreference.getGenreThird())
                .preferenceSad(memberPreference.getPreferenceSad())
                .preferenceHappy(memberPreference.getPreferenceHappy())
                .preferenceAngry(memberPreference.getPreferenceAngry())
                .preferenceRomance(memberPreference.getPreferenceRomance())
                .preferenceAnxious(memberPreference.getPreferenceAnxiety())
                .build();
    }
}
