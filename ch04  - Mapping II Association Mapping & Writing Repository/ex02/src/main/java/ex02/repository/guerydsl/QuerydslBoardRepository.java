package ex02.repository.guerydsl;

import ex02.domain.Board;
import ex02.domain.dto.BoardDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuerydslBoardRepository {
    BoardDto findById02(Integer id);

    List<Board> findAllByOrderByRegDateDesc02();

    List<BoardDto> findAllByOrderByRegDateDesc03();

    List<BoardDto> findAllByOrderByRegDateDesc03(Integer page, Integer size);

    List<BoardDto> findAll02(Pageable pageable);

    List<BoardDto> findAll02(String keyword, Pageable pageable);

    Long update(Board board);

    Long delete(Integer id);

    Long delete(Integer id, Integer userId);
}
