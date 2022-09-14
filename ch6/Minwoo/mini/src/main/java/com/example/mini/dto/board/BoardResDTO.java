package com.example.mini.dto.board;

import com.example.mini.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BoardResDTO {
    private Long id;
    private String author;
    private String title;
    private String description;
    private int thumb;
    private LocalDateTime created_timestamp;

    public static BoardResDTO of(Board board) {
        return BoardResDTO.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .description(board.getDescription())
                .thumb(board.getThumb())
                .created_timestamp(board.getCreated_timestamp())
                .build();
    }
}
