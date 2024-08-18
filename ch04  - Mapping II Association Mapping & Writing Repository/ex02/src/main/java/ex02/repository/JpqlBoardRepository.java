package ex02.repository;

import ex02.domain.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpqlBoardRepository {

    private final EntityManager em;

    public JpqlBoardRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Integer id) {
        return em.find(Board.class, id);
    }

    public Board findByIdInJpql(Integer id) {
        String qlString = "select b from Board b where b.id = :id";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<Board> findAll() {
        String jpql = "select b from Board b order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        return query.getResultList();
    }

    public List<Board> findAllWithInnerJoin() {
        String jpql = "select b from Board b inner join b.user u order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        return query.getResultList();
    }

    public List<Board> findAllWithFetchJoin() {
        String jpql = "select b from Board b join fetch b.user order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        return query.getResultList();
    }

    public List<Board> findAllWithFetchJoinAndPagination(Integer page, Integer size) {
        String jpql = "select b from Board b join fetch b.user order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public List<Board> findAllWithFetchJoinAndPaginationAndLikeSearch(String keyword, Integer page, Integer size) {
        String jpql = "select b from Board b join fetch b.user where b.title like :keywordContains or b.contents like :keywordContains order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);

        query.setParameter("keywordContains", "%" + keyword + "%");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public Board update(Board board) {
        Board boardPersisted = em.find(Board.class, board.getId());

        if (boardPersisted != null) {
            boardPersisted.setTitle(board.getTitle());
            boardPersisted.setContents(board.getContents());
        }

        return boardPersisted;
    }

    public Integer updateInJpql(Board board) {
        String jpql = "update Board b set b.title=:title, b.contents=:contents where b.id=:id";
        Query query = em.createQuery(jpql);

        query.setParameter("id", board.getId());
        query.setParameter("title", board.getTitle());
        query.setParameter("contents", board.getContents());
        return query.executeUpdate();
    }

    public void deleteById(Integer id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }

    public Integer deleteByIdInJpql(Integer id) {
        String jpql = "delete from Board b where b.id=:id";
        Query query = em.createQuery(jpql);

        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public Integer deleteByIdAndUserId(Integer id, Integer userId) {
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