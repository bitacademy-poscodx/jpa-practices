package ex02.repository;

import ex02.domain.Board;
import ex02.domain.User;
import ex02.domain.dto.BoardDto;
import ex02.domain.type.GenderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpqlBoardRepository {
    private static final User user01 = new User("고길동", "gildong@gmail.com", "1234", GenderType.male);
    private static final User user02 = new User("김정자", "joengja@gmail.com", "1234", GenderType.female);
    private static final Board board01 = new Board("제목1", "내용1", user01);
    private static final Board board02 = new Board("제목2", "내용2", user02);
    private static final Board board03 = new Board("제목3", "내용3", user02);
    private static Long countBoard;

    @Autowired
    private JpqlUserRepository userRepository;

    @Autowired
    private JpqlBoardRepository boardRepository;

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

        boardRepository.save(board03);
        assertNotNull(board03.getId());

        countBoard = boardRepository.count();
    }

    @Test
    @Order(1)
    @Transactional
    public void testFind() {
        Board board = boardRepository.find(board01.getId());

        assertNotNull(board);
        assertEquals("고길동", board.getUser().getName());
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindByIdWithNoJoin() {
        Board board = boardRepository.findByIdWithNoJoin(board02.getId());

        assertNotNull(board);
        assertEquals("김정자", board.getUser().getName());
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindByIdWithInnerJoin() {
        Board board = boardRepository.findByIdWithInnerJoin(board02.getId());

        assertNotNull(board);
        assertEquals("김정자", board.getUser().getName());
    }

    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindByIdWithInnerJoinAndProjection() {
        BoardDto board = boardRepository.findByIdWithInnerJoinAndProjection(board02.getId());

        assertNotNull(board);
        assertEquals("김정자", board.getUserName());
    }





    /*
    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAll() {
        List<Board> list = boardRepository.findAll();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithInnerJoin() {
        List<Board> list = boardRepository.findAllWithInnerJoin();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithFetchJoin() {
        List<Board> list = boardRepository.findAllWithFetchJoin();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithFetchJoinAndPagination() {
        List<Board> list = boardRepository.findAllWithFetchJoinAndPagination(1, 3);
        assertEquals(3, list.size());
    }

    @Test
    @Order(7)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithFetchJoinAndPaginationAndLikeSearch() {
        List<Board> list = boardRepository.findAllWithFetchJoinAndPaginationAndLikeSearch("내용", 1, 3);
        assertEquals(3, list.size());
    }

    @Test
    @Order(8)
    @Transactional
    @Rollback(false)
    public void testUpdate() {
        Board board = new Board();
        board.setId(board01.getId());
        board.setTitle("제목10");
        board.setContents("내용10");

        Board boardPersisted = boardRepository.update(board);
        assertEquals("고길동", boardPersisted.getUser().getName());
    }

    @Test
    @Order(9)
    @Transactional
    @Rollback(false)
    public void testUpdateInJpql() {
        Board board = new Board();
        board.setId(board02.getId());
        board.setTitle("제목20");
        board.setContents("내용20");

        Integer count = boardRepository.updateInJpql(board);
        assertEquals(1, count);
    }

    @Test
    @Order(10)
    @Transactional
    @Rollback(false)
    public void testDeleteById() {
        boardRepository.deleteById(board01.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(11)
    @Transactional
    @Rollback(false)
    public void testDeleteByIdInJpql() {
        Integer count = boardRepository.deleteByIdInJpql(board02.getId());
        assertEquals(1, count);
    }

    @Test
    @Order(12)
    @Transactional
    @Rollback(false)
    public void testDeleteByIdAndUserId() {
        Integer count = boardRepository.deleteByIdAndUserId(board03.getId(), user02.getId());
        assertEquals(1, count);
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp() {
        userRepository.deleteByIdInJpql(user01.getId());
        userRepository.deleteByIdInJpql(user02.getId());
    }
    */
}