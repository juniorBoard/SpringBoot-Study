package com.example.mini.controller;

import com.example.mini.domain.Board;
import com.example.mini.dto.board.BoardResDTO;
import com.example.mini.dto.board.CreateBoardDTO;
import com.example.mini.dto.board.UpdateBoardDTO;
import com.example.mini.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    BoardServiceImpl boardService;

    private static final Long ID = 1L;
    private static final String AUTHOR = "user";
    private static final String TITLE = "Hi";
    private static final String DESCRIPTION = "Nice to meet you";
    private static final LocalDateTime CREATED_TIMESTAMP = LocalDateTime.now();
    private static final int LIKE_0 = 0;
    private static final int LIKE_1 = 1;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysExpect(status().isOk())
                .alwaysExpect(jsonPath("$.id").exists())
                .alwaysExpect(jsonPath("$.author").exists())
                .alwaysExpect(jsonPath("$.title").exists())
                .alwaysExpect(jsonPath("$.description").exists())
                .alwaysExpect(jsonPath("$.thumb").exists())
                .alwaysExpect(jsonPath("$.created_timestamp").exists())
                .alwaysDo(print())
                .build();
    }

    @Test
    void getBoard() throws Exception {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        given(boardService.getBoard(ID)).willReturn(BoardResDTO.of(givenBoard));

        mockMvc.perform(get("/api/board/" + ID));
    }

    @Test
    void createBoard() throws Exception {
        CreateBoardDTO givenCreateBoardDTO = CreateBoardDTO.builder()
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();

        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        given(boardService.createBoard(any(givenCreateBoardDTO.getClass()))).willReturn(BoardResDTO.of(givenBoard));

        mockMvc.perform(
                post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"author\" : \"" + AUTHOR + "\"," +
                                "\"title\" : \"" + TITLE + "\"," +
                                "\"description\" : \"" + DESCRIPTION + "\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateBoard() throws Exception {
        final String UPDATE_TITLE = "Hello";
        final String UPDATE_DESCRIPTION = "Hello, Everyone";

        UpdateBoardDTO givenUpdateBoardDTO = UpdateBoardDTO.builder()
                .title(UPDATE_TITLE)
                .description(UPDATE_DESCRIPTION)
                .build();

        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(UPDATE_TITLE)
                .description(UPDATE_DESCRIPTION)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        given(boardService.updateBoard(any(ID.getClass()), any(givenUpdateBoardDTO.getClass()))).willReturn(BoardResDTO.of(givenBoard));

        mockMvc.perform(
                        put("/api/board/" + ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"title\" : \"" + UPDATE_TITLE + "\"," +
                                        "\"description\" : \"" + UPDATE_DESCRIPTION + "\"" +
                                        "}")
                                .accept(MediaType.APPLICATION_JSON));

    }

    @Test
    void removeBoard() {
    }

    @Test
    void likeBoard() throws Exception {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumb(LIKE_1)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        given(boardService.likeBoard(any(ID.getClass()))).willReturn(BoardResDTO.of(givenBoard));

        mockMvc.perform(put("/api/board/" + ID + "/like"));
    }

    @Test
    void unlikeBoard() throws Exception {
        Board givenBoard = Board.builder()
                .id(ID)
                .author(AUTHOR)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumb(LIKE_0)
                .created_timestamp(CREATED_TIMESTAMP)
                .build();

        given(boardService.unlikeBoard(any(ID.getClass()))).willReturn(BoardResDTO.of(givenBoard));

        mockMvc.perform(put("/api/board/" + ID + "/unlike"));
    }
}