package ex10.repository;

import ex10.domain.Genre;
import ex10.repository.querydsl.QuerydslGenreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>, QuerydslGenreRepository {
}