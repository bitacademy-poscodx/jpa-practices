package ex02.repository;

import ex02.domain.Board;
import ex02.domain.User;
import ex02.domain.type.GenderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestQuerydslBoardRepository {
    private static final User user01 = new User("고길동", "gildong@gmail.com", "1234", GenderType.male);
    private static final User user02 = new User("김정자", "joengja@gmail.com", "1234", GenderType.female);
    private static final Board board01 = new Board("제목1", "내용1", user01);
    private static final Board board02 = new Board("제목2", "내용2", user02);
    private static Long countBoard;

    @Autowired
    private QuerydslUserRepository userRepository;

    @Autowired
    private QuerydslBoardRepository boardRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave() {
        userRepository.save(user01);
        assertNotNull(user01.getId());

        userRepository.save(user02);
        assertNotNull(user02.getId());

        boardRepository.save(board01);
        assertNotNull(board01.getId());

        boardRepository.save(board02);
        assertNotNull(board02.getId());

        countBoard = boardRepository.count();
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById() {
        Board board = boardRepository.findById(board01.getId());

        assertNotNull(board);
        assertEquals("고길동", board.getUser().getName());
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAll() {
        List<Board> list = boardRepository.findAllWithNoJoin();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithInnerJoin() {
        List<Board> list = boardRepository.findAllWithOnlyInnerJoin();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithFetchJoin() {
        List<Board> list = boardRepository.findAll(Sort.Order.desc("regDate"));
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAll03Pagination() {
        List<Board> list = boardRepository.findAll(1, 2, Sort.Order.desc("regDate"));
        assertEquals(2, list.size());
    }

    @Test
    @Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithFetchJoinAndPagination() {
        List<Board> list = boardRepository.findAllByTitleContainingAndContentsContaining("내용", "내용", 1, 2, Sort.Order.desc("regDate"));
        assertEquals(2, list.size());
    }

    @Test
    @Order(7)
    @Transactional
    @Rollback(false)
    public void testUpdate() {
        Board board = new Board();
        board.setId(board01.getId());
        board.setTitle("제목10");
        board.setContents("내용10");

        assertEquals(1, boardRepository.update(board));
    }

    @Test
    @Order(8)
    @Transactional
    @Rollback(false)
    public void testDelete() {
        boardRepository.deleteById(board01.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(9)
    @Transactional
    @Rollback(false)
    public void testDeleteByIdAndUserId() {
        boardRepository.deleteByIdAndUserId(board02.getId(), user02.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp() {
        userRepository.deleteById(user01.getId());
        userRepository.deleteById(user02.getId());
    }
}