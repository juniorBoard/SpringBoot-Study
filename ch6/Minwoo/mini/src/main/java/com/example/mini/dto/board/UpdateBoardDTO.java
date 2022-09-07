package com.example.mini.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBoardDTO {
    private String title;
    private String description;
}
