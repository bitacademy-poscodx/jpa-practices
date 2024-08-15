package ex11.repository.querydsl;

import ex11.domain.Genre;
import java.util.List;

public interface QuerydslGenreRepository {
    Genre findById02(Integer id);
    List<Genre> findAll02();
}
