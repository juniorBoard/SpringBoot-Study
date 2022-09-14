package com.example.mini.service;

import com.example.mini.domain.Board;
import com.example.mini.dto.board.BoardResDTO;
import com.example.mini.dto.board.CreateBoardDTO;
import com.example.mini.dto.board.UpdateBoardDTO;
import com.example.mini.repository.BoardRepository;
import com.example.mini.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class BoardServiceTest {
    private BoardRepository boardRepository = Mockito.mock(BoardRepository.class);
    private BoardService boardService = new BoardServiceImpl(boardRepository);

    private final Long ID = 1L;
    private final String AUTHOR = "user";
    private final String TITLE = "Hi";
    private final String DESCRIPTION = "Nice to meet you";
    private final LocalDateTime CREATED_TIMESTAMP = LocalDateTime.now();
    private final int LIKE_0 = 0;
    private final int LIKE_1 = 1;

    @Test
    public void getBoardByIdTest() {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.of(givenBoard));
        BoardResDTO boardResDTO = boardService.getBoard(ID);

        assertEquals(ID, boardResDTO.getId());
        assertEquals(AUTHOR, boardResDTO.getAuthor());
        assertEquals(TITLE, boardResDTO.getTitle());
        assertEquals(DESCRIPTION, boardResDTO.getDescription());
        assertEquals(LIKE_0, boardResDTO.getThumb());
        assertEquals(CREATED_TIMESTAMP, boardResDTO.getCreated_timestamp());
    }

    @Test
    public void createBoardTest() {
        CreateBoardDTO givenCreateBoardDTO = CreateBoardDTO.builder()
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        Board givenBoard = Board.builder()
                .id(ID)
                .author(givenCreateBoardDTO.getAuthor())
                .title(givenCreateBoardDTO.getTitle())
                .description(givenCreateBoardDTO.getDescription())
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        Mockito.when(boardRepository.save(any(Board.class))).thenReturn(givenBoard);
        BoardResDTO boardResDTO = boardService.createBoard(givenCreateBoardDTO);

        assertEquals(ID, boardResDTO.getId());
        assertEquals(AUTHOR, boardResDTO.getAuthor());
        assertEquals(TITLE, boardResDTO.getTitle());
        assertEquals(DESCRIPTION, boardResDTO.getDescription());
        assertEquals(LIKE_0, boardResDTO.getThumb());
        assertEquals(CREATED_TIMESTAMP, boardResDTO.getCreated_timestamp());
    }

    @Test
    public void updateBoardTest() { // title, description 둘 중 하나만 변경할 경우 테스트는 생략
        final String UPDATE_TITLE = "Hello";
        final String UPDATE_DESCRIPTION = "Hello, Everyone";

        UpdateBoardDTO givenUpdateBoardDTO = UpdateBoardDTO.builder()
                .title(UPDATE_TITLE)
                .description(UPDATE_DESCRIPTION)
                .build();

        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.of(givenBoard));
        BoardResDTO boardResDTO = boardService.updateBoard(ID, givenUpdateBoardDTO);

        assertEquals(ID, boardResDTO.getId());
        assertEquals(AUTHOR, boardResDTO.getAuthor());
        assertEquals(UPDATE_TITLE, boardResDTO.getTitle());
        assertEquals(UPDATE_DESCRIPTION, boardResDTO.getDescription());
        assertEquals(LIKE_0, boardResDTO.getThumb());
        assertEquals(CREATED_TIMESTAMP, boardResDTO.getCreated_timestamp());
    }

    @Test
    public void deleteBoardByIdTest() {
    }

    @Test
    public void likeBoardTest() {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .thumb(LIKE_0)
                .build();

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.of(givenBoard));
        BoardResDTO boardResDTO = boardService.likeBoard(ID);

        assertEquals(ID, boardResDTO.getId());
        assertEquals(AUTHOR, boardResDTO.getAuthor());
        assertEquals(TITLE, boardResDTO.getTitle());
        assertEquals(DESCRIPTION, boardResDTO.getDescription());
        assertEquals(LIKE_0 + 1, boardResDTO.getThumb());
        assertEquals(CREATED_TIMESTAMP, boardResDTO.getCreated_timestamp());
    }

    @Test
    public void unlikeBoardTest() {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .thumb(LIKE_1)
                .build();

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.of(givenBoard));
        BoardResDTO boardResDTO = boardService.unlikeBoard(ID);

        assertEquals(ID, boardResDTO.getId());
        assertEquals(AUTHOR, boardResDTO.getAuthor());
        assertEquals(TITLE, boardResDTO.getTitle());
        assertEquals(DESCRIPTION, boardResDTO.getDescription());
        assertEquals(LIKE_1 - 1, boardResDTO.getThumb());
        assertEquals(CREATED_TIMESTAMP, boardResDTO.getCreated_timestamp());
    }

    @Test
    public void unlikeBoard_When_Like_is_Zero_Throw_RuntimeException_Test() {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .thumb(LIKE_0)
                .build();

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.of(givenBoard));

        assertThrows(RuntimeException.class, () -> boardService.unlikeBoard(ID));
    }
}