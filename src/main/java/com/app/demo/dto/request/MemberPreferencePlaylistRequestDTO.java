package com.app.demo.dto.request;

import com.app.demo.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class MemberPreferencePlaylistRequestDTO {

    @Data
    @Builder
    public static class UpdateMemberPreferencePlaylistDTO{
        Long memberId;
        LocalDate date;
    }
}
