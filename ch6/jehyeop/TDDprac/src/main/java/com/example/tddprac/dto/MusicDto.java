package com.example.tddprac.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicDto {
    private String title;
    private String artist;
    private String genre;
}
