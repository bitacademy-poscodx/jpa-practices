package ex10.repository.querydsl;

import ex10.domain.Genre;
import java.util.List;

public interface QuerydslGenreRepository {
    Genre findById02(Integer id);
    List<Genre> findAll02();
}
