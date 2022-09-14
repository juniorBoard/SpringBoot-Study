package com.study.serviceImpl;

import com.study.dto.CommentDto;
import com.study.dto.RegistryDto;
import com.study.model.Board;
import com.study.model.Comment;
import com.study.repository.BoardRepository;
import com.study.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import(BoardServiceImpl.class)
class BoardServiceImplTest {

    @MockBean
    BoardRepository boardRepository;

    @MockBean
    CommentRepository commentRepository;

    @Autowired
    BoardServiceImpl boardService;


    @Test
    public void getBoard(){
        //given
        Mockito.when(boardRepository.findAllByBoardId("3"))
                .thenReturn(new Board("3","title","main","writer"));

        Board board = boardService.getBoard("3");

        Assertions.assertEquals(board.getBoardId(), "3");
        Assertions.assertEquals(board.getBoardTitle(), "title");
        Assertions.assertEquals(board.getBoardMain(), "main");
        Assertions.assertEquals(board.getBoardWriter(), "writer");

        verify(boardRepository).findAllByBoardId("3");
    }

    @Test
    public void postBoard() {
        //given
        Mockito.when(boardRepository.save(new Board("3","title","main","writer")))
                .thenReturn(new Board("3","title","main","writer"));

        Board board = boardRepository.save(new Board("3","title","main","writer"));

        Assertions.assertEquals(board.getBoardId(), "3");
        Assertions.assertEquals(board.getBoardTitle(), "title");
        Assertions.assertEquals(board.getBoardMain(), "main");
        Assertions.assertEquals(board.getBoardWriter(), "writer");

        verify(boardRepository).save(new Board("3","title","main","writer"));
    }


    @Test
    public void postComment() {
        //given
        CommentDto commentDto = CommentDto.builder()
                .comment("hi")
                .commentWriter("pineApple")
                .build();

        Comment comment = Comment.builder()
                .commentId(3L)
                .comment("hi")
                .commentWriter("pineApple")
                .build();

        Mockito.when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);

        RegistryDto registryDto = boardService.saveComment(commentDto);


        Assertions.assertEquals(registryDto.getCommentId(), 3L);
        Assertions.assertEquals(registryDto.getComment(), "hi");
        Assertions.assertEquals(registryDto.getCommentWriter(), "pineApple");

    }
}