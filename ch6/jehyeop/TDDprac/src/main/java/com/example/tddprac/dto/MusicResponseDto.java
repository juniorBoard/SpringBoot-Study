package com.example.tddprac.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicResponseDto {
    private Long Id;
    private String title;
    private String artist;
    private String genre;
}
