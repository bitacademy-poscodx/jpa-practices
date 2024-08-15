package ex09.repository;

import ex09.domain.Song;
import ex09.repository.querydsl.QuerydslSongRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer>, QuerydslSongRepository {
}
