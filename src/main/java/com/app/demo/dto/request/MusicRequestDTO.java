package com.app.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MusicRequestDTO {
    @NotNull
    private String keyword;
}
