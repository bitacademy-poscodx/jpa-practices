package ex02.repository.guerydsl;

import ex02.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuerydslBoardRepository<R, ID> {



    <T> T findById(Class<T> clazzDto, Integer id);

    // <S extends R> List<S> findAll();

    Page<R> findAll(Pageable pageable);

    List<Board> findAll(Sort.Order... orders);

    List<Board> findAll(int page, int size, Sort.Order... orders);

    List<Board> findAllByTitleContainingOrContentsContaining(String title, String contents, int page, int size, Sort.Order... orders);

    <T> List<T> findAll(Class<T> classDto, Sort.Order... orders);

    <T> List<T> findAll(Class<T> classDto, int page, int size, Sort.Order... orders);

    <T> List<T> findAll(Class<T> classDto, Pageable pageable);

    <T> List<T> findAllByTitleContainingOrContentsContaining(Class<T> classDto, String title, String contents, int page, int size, Sort.Order... orders);

    <T> List<T> findAllByTitleContainingOrContentsContaining(Class<T> classDto, String title, String contents, Pageable pageable);

    Long update(Board board);

    Long delete(Integer id);

    Long delete(Integer id, Integer userId);
}
