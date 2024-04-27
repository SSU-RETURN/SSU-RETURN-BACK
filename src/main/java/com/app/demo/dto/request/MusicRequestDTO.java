package com.app.demo.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class MusicRequestDTO {

    @Getter
    @Setter
    public static class SearchMusicDTO{
        @NotNull
        private String keyword;
    }

    @Getter
    @Setter
    public static class SaveMusicDTO{
        private String title;
        private String artist;
        private String pictureKey;
    }
}
