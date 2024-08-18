package ex01.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex01.domain.Guestbook;
import ex01.domain.dto.GuestbookDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static ex01.domain.QGuestbook.guestbook;

@Repository
public class QuerydslGuestbookRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public QuerydslGuestbookRepository(JPAQueryFactory queryFactory) {
        super(Guestbook.class);
        this.queryFactory = queryFactory;
    }

    public void save(Guestbook guestbook) {
        Objects.requireNonNull(getEntityManager()).persist(guestbook);
    }

    public List<Guestbook> findAll() {
        return queryFactory
                .select(guestbook)
                .from(guestbook)
                .orderBy(guestbook.regDate.desc())
                .fetch();
    }

    public List<GuestbookDto> findAllWithProjection() {
        return queryFactory
                .select(Projections.constructor(GuestbookDto.class, guestbook.id, guestbook.name, guestbook.contents, guestbook.regDate))
                .from(guestbook)
                .orderBy(guestbook.regDate.desc())
                .fetch();
    }

    public Long deleteByIdAndPassword(Integer id, String password) {
        return queryFactory
                .delete(guestbook)
                .where(guestbook.id.eq(id).and(guestbook.password.eq(password)))
                .execute();
    }

    public Long count() {
        return queryFactory
                .select(Wildcard.count)
                .from(guestbook)
                .fetchOne();
    }
}