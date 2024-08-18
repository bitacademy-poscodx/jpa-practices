package ex02.repository.guerydsl;

import ex02.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuerydslBoardRepository {
    <R> R findById(Class<R> clazzDto, Integer id);

    List<Board> findAll(Sort.Order... orders);

    List<Board> findAll(int page, int size, Sort.Order... orders);

    List<Board> findAllByTitleContainingOrContentsContaining(String title, String contents, int page, int size, Sort.Order... orders);

    <R> List<R> findAll(Class<R> classDto, Sort.Order... orders);

    <R> List<R> findAll(Class<R> classDto, int page, int size, Sort.Order... orders);

    <R> List<R> findAll(Class<R> classDto, Pageable pageable);

    <R> List<R> findAllByTitleContainingOrContentsContaining(Class<R> classDto, String title, String contents, int page, int size, Sort.Order... orders);

    <R> List<R> findAllByTitleContainingOrContentsContaining(Class<R> classDto, String title, String contents, Pageable pageable);

    Long update(Board board);

    Long delete(Integer id);

    Long delete(Integer id, Integer userId);
}
