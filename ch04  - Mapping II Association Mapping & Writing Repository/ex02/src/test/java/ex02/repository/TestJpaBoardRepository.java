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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpaBoardRepository {
    private static final User user01 = new User("고길동", "gildong@gmail.com", "1234", GenderType.male);
    private static final User user02 = new User("김정자", "joengja@gmail.com", "1234", GenderType.female);
    private static final Board board01 = new Board("제목1", "내용1", user01);
    private static final Board board02 = new Board("제목2", "내용2", user01);
    private static final Board board03 = new Board("제목3", "내용3", user02);
    private static final Board board04 = new Board("제목4", "내용4", user02);
    private static Long countBoard;

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaBoardRepository boardRepository;

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
        assertNotNull(board01.getId());

        boardRepository.save(board03);
        assertNotNull(board03.getId());

        boardRepository.save(board04);
        assertNotNull(board04.getId());

        countBoard = boardRepository.count();
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById() {
        Optional<Board> optionalBoard = boardRepository.findById(board01.getId());
        assertEquals("고길동", optionalBoard.get().getUser().getName());
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindByIdWithProjection() {
        BoardDto board = boardRepository.findById(BoardDto.class, board03.getId());

        assertNotNull(board);
        assertEquals("김정자", board.getUserName());
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByOrderByRegDateDesc() {
        List<Board> boards = boardRepository.findAllByOrderByRegDateDesc();
        assertEquals(countBoard, boards.size());
    }

    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithSort() {
        List<Board> boards = boardRepository.findAll(Sort.Order.desc("regDate"));
        assertEquals(countBoard, boards.size());
    }

    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithProjectionAndSort(){
        List<BoardDto> boards = boardRepository.findAll(BoardDto.class, Sort.Order.desc("regDate"));
        assertEquals(countBoard, boards.size());
    }

    @Test
    @Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithProjectionAndPaginationAndSort01(){
        List<BoardDto> boards = boardRepository.findAll(BoardDto.class, 1, 4, Sort.Order.desc("regDate"));
        assertEquals(4, boards.size());
    }


    @Test
    @Order(7)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithProjectionAndPaginationAndSort02(){
        List<BoardDto> boards = boardRepository.findAll(BoardDto.class, PageRequest.of(1, 4, Sort.Direction.DESC, "regDate"));
        assertEquals(4, boards.size());
    }

    @Test
    @Order(8)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByTitleContaingAndContentsContaining(){
        List<BoardDto> boards = boardRepository.findAllByTitleContainingOrContentsContaining(BoardDto.class, "테스트", "내용", PageRequest.of(1, 4, Sort.Direction.DESC, "regDate"));
        assertEquals(4, boards.size());
    }

    /*
    @Test
    @Order(9)
    @Transactional
    @Rollback(false)
    public void testUpdate01() {
        Board board = boardRepository.findById(board01.getId()).get();
        board.setTitle("제목10");
        board.setContents("내용10");

        assertEquals("고길동", board.getUser().getName());
    }

    @Test
    @Order(10)
    @Transactional
    @Rollback(false)
    public void testUpdate02() {
        Board board = new Board();
        board.setId(board02.getId());
        board.setTitle("제목20");
        board.setContents("내용20");

        assertEquals(1, boardRepository.update(board));
    }

    @Test
    @Order(11)
    @Transactional
    @Rollback(false)
    public void testDelete01() {
        Board board = boardRepository.findById(board01.getId()).get();
        boardRepository.delete(board);
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(12)
    @Transactional
    @Rollback(false)
    public void testDelete02() {
        boardRepository.deleteById(board02.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(13)
    @Transactional
    @Rollback(false)
    public void testDelete03() {
        boardRepository.delete(board03.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(14)
    @Transactional
    @Rollback(false)
    public void testDelete04() {
        boardRepository.delete(board04.getId(), user02.getId());
        assertEquals(--countBoard, boardRepository.count());

        userRepository.deleteById(user01.getId());
        userRepository.deleteById(user02.getId());
    }

 */
}