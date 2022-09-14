package com.example.mini.service.impl;

import com.example.mini.domain.Board;
import com.example.mini.dto.board.BoardResDTO;
import com.example.mini.dto.board.CreateBoardDTO;
import com.example.mini.dto.board.UpdateBoardDTO;
import com.example.mini.repository.BoardRepository;
import com.example.mini.service.BoardService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardResDTO getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        return BoardResDTO.of(board);
    }

    @Override
    public BoardResDTO createBoard(CreateBoardDTO dto) {
        Board board = boardRepository.save(dto.toEntity());
        return BoardResDTO.of(board);
    }

    @Override
    @Transactional
    public BoardResDTO updateBoard(Long id, UpdateBoardDTO dto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        if (dto.getTitle() != null) board.updateTitle(dto.getTitle());
        if (dto.getDescription() != null) board.updateDescription(dto.getDescription());
        return BoardResDTO.of(board);
    }

    @Override
    public void removeBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BoardResDTO likeBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        board.like();
        return BoardResDTO.of(board);
    }

    @Override
    @Transactional
    public BoardResDTO unlikeBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        board.unlike();
        return BoardResDTO.of(board);
    }
}
