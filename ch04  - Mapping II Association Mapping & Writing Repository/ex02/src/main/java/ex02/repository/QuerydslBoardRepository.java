package ex02.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.Board;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
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
        return queryFactory
                .selectFrom(board)
                .where(board.id.eq(id))
                .fetchOne();
    }

    public List<Board> findAllWithNoJoin() {
        return queryFactory
                .selectFrom(board)
                .orderBy(board.regDate.desc())
                .fetch();
    }

    public List<Board> findAllWithOnlyInnerJoin() {
        return queryFactory
                .selectFrom(board)
                .innerJoin(board.user())
                .orderBy(board.regDate.desc())
                .fetch();
    }

    public List<Board> findAll(Sort.Order... orders) {
        return queryFactory
                .selectFrom(board)
                .innerJoin(board.user())
                .fetchJoin()
                .orderBy(Arrays
                        .stream(orders)
                        .map(o -> new OrderSpecifier(o.isAscending() ? ASC : DESC, new PathBuilder(Board.class, "board").get(o.getProperty())))
                        .toArray(OrderSpecifier[]::new))
                .fetch();
    }

    public List<Board> findAll(int page, int size, Sort.Order... orders) {
        return queryFactory
                .selectFrom(board)
                .innerJoin(board.user())
                .fetchJoin()
                .orderBy(Arrays
                        .stream(orders)
                        .map(o -> new OrderSpecifier(o.isAscending() ? ASC : DESC, new PathBuilder(Board.class, "board").get(o.getProperty())))
                        .toArray(OrderSpecifier[]::new))
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }

    public List<Board> findAllByTitleContainingAndContentsContaining(String title, String contents, int page, int size, Sort.Order... orders) {
        return queryFactory
                .selectFrom(board)
                .innerJoin(board.user())
                .fetchJoin()
                .where(new BooleanBuilder().andAnyOf(Optional.ofNullable(title).map(board.title::contains).orElse(null), Optional.ofNullable(contents).map(board.contents::contains).orElse(null)))
                .orderBy(Arrays
                        .stream(orders)
                        .map(o -> new OrderSpecifier(o.isAscending() ? ASC : DESC, new PathBuilder(Board.class, "board").get(o.getProperty())))
                        .toArray(OrderSpecifier[]::new))
                .offset((long) (page - 1) * size)
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