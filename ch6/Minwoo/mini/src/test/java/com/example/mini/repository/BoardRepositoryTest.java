package com.example.mini.repository;

import com.example.mini.domain.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {
    private final String AUTHOR = "user";
    private final String TITLE = "Hi";
    private final String DESCRIPTION = "Nice to meet you";
    private final int INIT_LIKE = 0;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void saveTest() {
        Board board = Board.builder()
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        Board saved = boardRepository.save(board);
        Board found = boardRepository.findById(saved.getId()).get();

        assertNotNull(found.getId());
        assertEquals(AUTHOR, found.getAuthor());
        assertEquals(TITLE, found.getTitle());
        assertEquals(DESCRIPTION, found.getDescription());
        assertEquals(INIT_LIKE, found.getThumb());
        assertNotNull(found.getCreated_timestamp());
    }
}