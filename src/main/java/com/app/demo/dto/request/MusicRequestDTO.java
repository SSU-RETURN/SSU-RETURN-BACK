package com.app.demo.dto.request;

import com.app.demo.dto.response.MusicResponseDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
        private List<MusicContentDTO> musics;
    }

    @Getter
    @Setter
    public static class MusicContentDTO {
        private String artist;
        private String title;
        private String pictureKey;
    }

}
