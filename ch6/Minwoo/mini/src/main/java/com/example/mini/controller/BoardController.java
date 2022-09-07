package com.example.mini.controller;

import com.example.mini.dto.board.BoardResDTO;
import com.example.mini.dto.board.CreateBoardDTO;
import com.example.mini.dto.board.UpdateBoardDTO;
import com.example.mini.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{id}")
    public BoardResDTO getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("")
    public BoardResDTO createBoard(@RequestBody CreateBoardDTO dto) {
        return boardService.createBoard(dto);
    }

    @PutMapping("/{id}")
    public BoardResDTO updateBoard(@PathVariable Long id, @RequestBody UpdateBoardDTO dto) {
        return boardService.updateBoard(id, dto);
    }

    @DeleteMapping("/{id}")
    public void removeBoard(@PathVariable Long id) {
        boardService.removeBoard(id);
    }

    @PutMapping("/{id}/like")
    public BoardResDTO likeBoard(@PathVariable Long id) {
        return boardService.likeBoard(id);
    }

    @PutMapping("/{id}/unlike")
    public BoardResDTO unlikeBoard(@PathVariable Long id) {
        return boardService.unlikeBoard(id);
    }
}
