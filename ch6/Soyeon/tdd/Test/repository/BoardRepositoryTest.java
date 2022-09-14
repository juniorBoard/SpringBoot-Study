package com.study.repository;

import com.study.model.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest //  JPA Repository들에 대한 빈들을 등록하여 단위 테스트의 작성을 용이하게 함
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    private static final String boardId = "250";
    private static final String boardTitle = "hi";
    private static final String boardMain = "start!";
    private static final String boardWriter = "apple";

    @Test
    public void saveBoard(){

        //given
        Board board = Board.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardMain(boardMain)
                .boardWriter(boardWriter)
                .build();

        //when
        Board result = boardRepository.save(board);

        //then
        assertThat(result.getBoardId()).isNotNull();
        assertThat(result.getBoardTitle()).isEqualTo("hi");
        assertThat(result.getBoardMain()).isEqualTo("start!");
        assertThat(result.getBoardWriter()).isEqualTo("apple");
    }
}