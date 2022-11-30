package com.waterfogsw.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterfogsw.board.core.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
