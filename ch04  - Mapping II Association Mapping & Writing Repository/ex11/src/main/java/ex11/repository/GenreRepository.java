package ex11.repository;

import ex11.domain.Genre;
import ex11.repository.querydsl.QuerydslGenreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>, QuerydslGenreRepository {
}