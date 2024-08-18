package ex02.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.Board;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static ex02.domain.QBoard.board;

@Repository
public class QuerydslBoardRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    public QuerydslBoardRepository(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    public void save(Board board) {
        getEntityManager().persist(board);
    }

    public Board findById(Integer id) {
        return (Board) queryFactory
                .from(board)
                .where(board.id.eq(id))
                .fetchOne();
    }

    public List<Board> findAll() {
        return queryFactory
                .select(board)
                .from(board)
                .orderBy(board.regDate.desc())
                .fetch();
    }

    public List<Board> findAllWithInnerJoin() {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user())
                .orderBy(board.regDate.desc())
                .fetch();
    }

    public List<Board> findAllWithFetchJoin() {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin()
                .orderBy(board.regDate.desc())
                .fetch();
    }

    public List<Board> findAllWithFetchJoinAndPagination(Integer page, Integer size) {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin()
                .offset((page - 1) * size)
                .limit(size)
                .orderBy(board.regDate.desc())

                .fetch();
    }

    public List<Board> findAllWithFetchJoinAndPaginationAndLikeSearchs(String keyword, Integer page, Integer size) {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin()
                .where(board.title.contains(keyword).or(board.contents.contains(keyword)))
                .orderBy(board.regDate.desc())
                .offset((page - 1) * size)
                .limit(size)
                .fetch();
    }

    public Long update(Board argBoard) {
        return queryFactory
                .update(board)
                .set(board.title, argBoard.getTitle())
                .set(board.contents, argBoard.getContents())
                .where(board.id.eq(argBoard.getId()))
                .execute();
    }

    public Long deleteById(Integer id) {
        return queryFactory
                .delete(board)
                .where(board.id.eq(id))
                .execute();
    }

    public Long deleteByIdAndUserId(Integer id, Integer userId) {
        return queryFactory
                .delete(board)
                // .where(board.id.eq(id).and(board.user().id.eq(userId)))
                .where(board.id.eq(id), board.user().id.eq(userId))
                .execute();
    }

    public Long count() {
        return queryFactory
                .from(board)
                .fetchCount();
    }
}