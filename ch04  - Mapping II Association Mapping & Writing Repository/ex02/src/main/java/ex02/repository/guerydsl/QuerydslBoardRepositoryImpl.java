package ex02.repository.guerydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.Board;
import ex02.domain.dto.FieldPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static ex02.domain.QBoard.board;

@Slf4j
@Repository
public class QuerydslBoardRepositoryImpl extends QuerydslRepositorySupport implements QuerydslBoardRepository {

    private final JPAQueryFactory queryFactory;

    public QuerydslBoardRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public <T> T findById(Class<T> classDto, Integer id) {
        return queryFactory
                .select(Projections.fields(classDto, Arrays.stream(classDto.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> {
                    String fieldName = Optional
                            .ofNullable(field.getAnnotation(FieldPath.class))
                            .filter(f -> !"".equals(f.value()))
                            .map(FieldPath::value)
                            .orElse(field.getName());

                    return new PathBuilder<>(Object.class, "board").get(fieldName, field.getType());
                }))))
                .from(board)
                .innerJoin(board.user())
                .where(board.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Board> findAll(Sort.Order... orders) {
        return _findAll(Optional.empty(), Optional.empty(), Optional.empty(), orders);
    }

    @Override
    public List<Board> findAll(int page, int size, Sort.Order... orders) {
        return _findAll(Optional.empty(), Optional.of(page), Optional.of(size), orders);
    }

    @Override
    public List<Board> findAllByTitleContainingOrContentsContaining(String title, String contents, int page, int size, Sort.Order... orders) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.andAnyOf(Optional.ofNullable(title).map(board.title::contains).orElse(null), Optional.ofNullable(contents).map(board.contents::contains).orElse(null));

        return _findAll(Optional.of(builder), Optional.of(page), Optional.of(size), orders);
    }

    @Override
    public <T> List<T> findAll(Class<T> classDto, Sort.Order... orders) {
        return _findAll(classDto, Optional.empty(), Optional.empty(), Optional.empty(), orders);
    }

    @Override
    public <T> List<T> findAll(Class<T> classDto, int page, int size, Sort.Order... orders) {
        return _findAll(classDto, Optional.empty(), Optional.of(page), Optional.of(size), orders);
    }

    @Override
    public <T> List<T> findAll(Class<T> classDto, Pageable pageable) {
        return findAll(classDto, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().stream().toArray(Sort.Order[]::new));
    }

    @Override
    public <T> List<T> findAllByTitleContainingOrContentsContaining(Class<T> classDto, String title, String contents, int page, int size, Sort.Order... orders) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.andAnyOf(Optional.ofNullable(title).map(board.title::contains).orElse(null), Optional.ofNullable(contents).map(board.contents::contains).orElse(null));

        return _findAll(classDto, Optional.of(builder), Optional.of(page), Optional.of(size), orders);
    }

    @Override
    public <T> List<T> findAllByTitleContainingOrContentsContaining(Class<T> classDto, String title, String contents, Pageable pageable) {
        return findAllByTitleContainingOrContentsContaining(classDto, title, contents, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().stream().toArray(Sort.Order[]::new));
    }

    private List<Board> _findAll(Optional<BooleanBuilder> builder, Optional<Integer> page, Optional<Integer> size, Sort.Order... orders) {
        final JPAQuery<Board> query = queryFactory
                .selectFrom(board)
                .innerJoin(board.user())
                .fetchJoin()
                .where(builder.orElseGet(BooleanBuilder::new))
                .orderBy(Arrays
                        .stream(orders)
                        .map(o -> new OrderSpecifier(o.isAscending() ? ASC : DESC, Expressions.path(Board.class, board, o.getProperty())))
                        .toArray(OrderSpecifier[]::new));

        page.ifPresent(pg -> query.offset((long) (pg - 1) * size.orElse(0)));
        size.ifPresent(query::limit);

        return query.fetch();
    }

    private <T> List<T> _findAll(Class<T> classDto, Optional<BooleanBuilder> builder, Optional<Integer> page, Optional<Integer> size, Sort.Order... orders) {
        JPAQuery<T> query = queryFactory
                .select(Projections.fields(classDto, Arrays.stream(classDto.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> {
                    String fieldName = Optional
                            .ofNullable(field.getAnnotation(FieldPath.class))
                            .filter(f -> !"".equals(f.value()))
                            .map(FieldPath::value)
                            .orElse(field.getName());

                    return new PathBuilder<>(Object.class, "board").get(fieldName, field.getType());
                }))))
                .from(board)
                .innerJoin(board.user())
                .where(builder.orElseGet(BooleanBuilder::new))
                .orderBy(Arrays
                        .stream(orders)
                        .map(o -> new OrderSpecifier(o.isAscending() ? ASC : DESC, Expressions.path(Board.class, board, o.getProperty())))
                        .toArray(OrderSpecifier[]::new));

        page.ifPresent(pg -> query.offset((long) (pg - 1) * size.orElse(0)));
        size.ifPresent(query::limit);

        return query.fetch();
    }

    @Override
    public Long update(Board argBoard) {
        return queryFactory
                .update(board)
                .set(board.title, argBoard.getTitle())
                .set(board.contents, argBoard.getContents())
                .where(board.id.eq(argBoard.getId()))
                .execute();
    }

    @Override
    public Long delete(Integer id) {
        return queryFactory
                .delete(board)
                .where(board.id.eq(id))
                .execute();
    }

    @Override
    public Long delete(Integer id, Integer userId) {
        return queryFactory
                .delete(board)
                .where(board.id.eq(id).and(board.user().id.eq(userId)))
                .execute();
    }
}
