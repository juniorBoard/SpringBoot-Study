package com.study.serviceImpl;

import com.study.dto.BoardDto;
import com.study.dto.CommentDto;
import com.study.dto.RegistryDto;
import com.study.model.Board;
import com.study.model.Comment;
import com.study.repository.BoardRepository;
import com.study.repository.CommentRepository;
import com.study.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Board getBoard(String boardId) {
        Board board = boardRepository.findAllByBoardId(boardId);
        return board;
    }

    public Board saveBoard(BoardDto boardDto) {
        Board board = new Board(boardDto);
        boardRepository.save(board);
        return board;
    }

    public RegistryDto saveComment(CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setCommentWriter(commentDto.getCommentWriter());
        commentRepository.save(comment);

        Comment saveComment = commentRepository.save(comment);
        RegistryDto registryDto = RegistryDto.builder()
                .commentId(saveComment.getCommentId())
                .comment(saveComment.getComment())
                .commentWriter(saveComment.getCommentWriter())
                .build();

        return registryDto;
    }
}

