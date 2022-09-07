package com.example.mini.service;

import com.example.mini.dto.board.BoardResDTO;
import com.example.mini.dto.board.CreateBoardDTO;
import com.example.mini.dto.board.UpdateBoardDTO;

public interface BoardService {
    BoardResDTO getBoard(Long id);
    BoardResDTO createBoard(CreateBoardDTO dto);
    BoardResDTO updateBoard(Long id, UpdateBoardDTO dto);
    void removeBoard(Long id);
    BoardResDTO likeBoard(Long id);
    BoardResDTO unlikeBoard(Long id);
}
