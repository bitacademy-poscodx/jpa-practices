package ex09.repository;

import ex09.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
    Genre findByName(String name);
}
