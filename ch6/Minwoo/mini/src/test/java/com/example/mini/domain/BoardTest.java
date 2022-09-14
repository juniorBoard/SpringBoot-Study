package com.example.mini.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void likeTest() {
        final String AUTHOR = "user";
        final String TITLE = "Hi";
        final String DESCRIPTION = "Nice to meet you";

        final int EXPECTED = 1;
        Board board =  Board.builder()
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        board.like();

        assertEquals(EXPECTED, board.getThumb());
    }

    @Test
    public void unlikeTest() {
        final String AUTHOR = "user";
        final String TITLE = "Hi";
        final String DESCRIPTION = "Nice to meet you";

        final int EXPECTED = 0;
        Board board =  Board.builder()
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumb(1)
                .build();

        board.unlike();

        assertEquals(EXPECTED, board.getThumb());
    }

    @Test
    public void unlikeTest_when_like_is_zero_throw_RuntimeException() {
        final String AUTHOR = "user";
        final String TITLE = "Hi";
        final String DESCRIPTION = "Nice to meet you";

        Board board =  Board.builder()
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();


        assertThrows(RuntimeException.class, () -> board.unlike());
    }
}