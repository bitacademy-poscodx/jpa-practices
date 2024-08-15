package ex11.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ex11.domain.Genre;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

import static ex11.domain.QGenre.genre;
import static ex11.domain.QSong.song;

public class QuerydslGenreRepositoryImpl extends QuerydslRepositorySupport implements QuerydslGenreRepository {

    private final JPAQueryFactory queryFactory;

    public QuerydslGenreRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Genre.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Genre findById02(Integer id) {
        return queryFactory
                .selectDistinct(genre)
                .from(genre)
                .leftJoin(genre.songs, song)
                .fetchJoin()
                .where(genre.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Genre> findAll02() {
        return queryFactory
                .selectDistinct(genre)
                .from(genre)
                .leftJoin(genre.songs, song)
                .fetchJoin()
                .fetch();
    }
}