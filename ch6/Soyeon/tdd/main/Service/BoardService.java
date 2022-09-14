package com.study.service;

import com.study.dto.BoardDto;
import com.study.dto.CommentDto;
import com.study.dto.RegistryDto;
import com.study.model.Board;

public interface BoardService {

    Board getBoard(String boardId);

    Board saveBoard(BoardDto boardDto);

    RegistryDto saveComment(CommentDto commentDto);
}
