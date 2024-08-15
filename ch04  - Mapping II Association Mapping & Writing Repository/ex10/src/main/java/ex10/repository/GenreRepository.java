package ex10.repository;

import ex10.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
    Genre findByName(String name);
}
