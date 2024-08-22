package ex02.repository;

import ex02.domain.Board;
import ex02.domain.dto.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Repository
public class JpqlBoardRepository {

    private final EntityManager em;

    public JpqlBoardRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Board board) {
        em.persist(board);
    }

    public Board find(int id) {
        return em.find(Board.class, id);
    }

    public Board findByIdWithNoJoin(int id) {
        String jpql = "select b from Board b where b.id = :id";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public Board findByIdWithInnerJoin(int id) {
        String jpql = "select b from Board b join b.user u where b.id = :id";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public BoardDto findByIdWithInnerJoinAndProjection(int id) {
        String jpql = "select new ex02.domain.dto.BoardDto(b.id, b.hit, b.title, b.contents, b.regDate, u.name) from Board b join b.user u where b.id = :id";
        TypedQuery<BoardDto>  query = em.createQuery(jpql, BoardDto.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public Board findById(int id) {
        String jpql = "select b from Board b join fetch b.user where b.id = :id";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<Board> findAllWithNoJoin() {
        String jpql = "select b from Board b";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        return query.getResultList();
    }

    public List<BoardDto> findAllWithInnerJoinAndProjection() {
        String jpql = "select new ex02.domain.dto.BoardDto(b.id, b.hit, b.title, b.contents, b.regDate, u.name) from Board b inner join b.user u";
        TypedQuery<BoardDto> query = em.createQuery(jpql, BoardDto.class);

        return query.getResultList();
    }

    public List<Board> findAll() {
        String jpql = "select b from Board b join fetch b.user";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        return query.getResultList();
    }

    public List<Board> findAllOrderByRegDateDesc() {
        String jpql = "select b from Board b join fetch b.user order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        return query.getResultList();
    }

    public List<Board> findTopKOrderByRegDateDesc(int page, int k) {
        String jpql = "select b from Board b join fetch b.user order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setFirstResult((page - 1) * k); // offset
        query.setMaxResults(k);

        return query.getResultList();
    }

    public List<Board> findTopKByTitleContainisAndContensContainsOrderByRegDateDesc(String title, String contents, int page, int k) {
        String jpql = "select b from Board b join fetch b.user where b.title like :titleContains or b.contents like :contentsContains order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setParameter("titleContains", "%" + title + "%");
        query.setParameter("contentsContains", "%" + contents + "%");
        query.setFirstResult((page - 1) * k); // offset
        query.setMaxResults(k);

        return query.getResultList();
    }

    public Board findAndUpdate(Board argBoard) {
        Board board = em.find(Board.class, argBoard.getId());

        if (board != null) {
            board.setTitle(argBoard.getTitle());
            board.setContents(argBoard.getContents());
        }

        return board;
    }

    public int update(Board board) {
        String jpql = "update Board b set b.title=:title, b.contents=:contents where b.id=:id";
        Query query = em.createQuery(jpql);

        query.setParameter("id", board.getId());
        query.setParameter("title", board.getTitle());
        query.setParameter("contents", board.getContents());
        return query.executeUpdate();
    }

    public void findAndDelete(int id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }

    public int deleteById(int id) {
        String jpql = "delete from Board b where b.id=:id";
        Query query = em.createQuery(jpql);

        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public int deleteByIdAndUserId(int id, int userId) {
        String jpql = "delete from Board b where b.id = ?1 and b.user.id = ?2";
        Query query = em.createQuery(jpql);

        query.setParameter(1, id);
        query.setParameter(2, userId);
        return query.executeUpdate();
    }

    public Long count() {
        String jpql = "select count(b) from Board b";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);

        return query.getSingleResult();
    }
}