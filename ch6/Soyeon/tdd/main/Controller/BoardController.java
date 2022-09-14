package com.study.controller;

import com.study.dto.BoardDto;
import com.study.dto.CommentDto;
import com.study.dto.RegistryDto;
import com.study.model.Board;
import com.study.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/registry")
    public Board getBoard(String boardId){
        return boardService.getBoard(boardId);
    }


    @PostMapping("/registry")
    public Board saveBoard(@RequestBody BoardDto boardDto){
        return boardService.saveBoard(boardDto);
    }


    @PostMapping("/registry/board")
    public ResponseEntity<RegistryDto> saveComment(@RequestBody CommentDto commentDto) {
        RegistryDto registryDto = boardService.saveComment(commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(registryDto);
    }

}
