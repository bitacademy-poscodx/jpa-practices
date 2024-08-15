package ex09.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ex09.domain.Song;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.Query;
import java.util.List;

import static ex09.domain.QSong.song;
import static ex09.domain.QGenre.genre;

public class QuerydslSongRepositoryImpl extends QuerydslRepositorySupport implements QuerydslSongRepository {
    private final JPAQueryFactory queryFactory;

    public QuerydslSongRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Song.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Song findById02(Integer id) {
        return queryFactory
                .selectDistinct(song)
                .from(song)
                .leftJoin(song.genres, genre)
                .fetchJoin()
                .where(song.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Song> findAll02() {
        return queryFactory
                .selectDistinct(song)
                .from(song)
                .leftJoin(song.genres, genre)
                .fetchJoin()
                .fetch();
    }

    @Override
    public void deleteGenreByIdAndGenreId(Integer id, Integer genreId) {
        String sql = "delete from song_genre where song_no=? and genre_no=?";
        Query query = getEntityManager().createNativeQuery(sql);

        query.setParameter(1, id);
        query.setParameter(2, genreId);

        query.executeUpdate();
    }
}