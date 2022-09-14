package com.example.mini.dto.board;

import com.example.mini.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateBoardDTO {
    private String author;
    private String title;
    private String description;

    public Board toEntity() {
        return Board.builder()
                .author(this.author)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
