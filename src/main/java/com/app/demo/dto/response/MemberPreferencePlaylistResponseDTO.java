package com.app.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberPreferencePlaylistResponseDTO {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateMemberPreferencePlaylistDTO{
        Long memberPreferencePlaylistId;
    }


    /*
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TestDTO{

    }

     */
}
