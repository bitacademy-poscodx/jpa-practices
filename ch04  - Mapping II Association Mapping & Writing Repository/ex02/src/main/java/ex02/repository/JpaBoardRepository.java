package ex02.repository;

import ex02.repository.guerydsl.QuerydslBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import ex02.domain.Board;

import java.util.List;

public interface JpaBoardRepository extends JpaRepository<Board, Integer>, QuerydslBoardRepository<Board, Integer> {
    List<Board> findAllByOrderByRegDateDesc();
}

