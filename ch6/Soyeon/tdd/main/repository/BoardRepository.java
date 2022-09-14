package com.study.repository;

import com.study.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String> {
    Board findAllByBoardId(String BoardId);
}
