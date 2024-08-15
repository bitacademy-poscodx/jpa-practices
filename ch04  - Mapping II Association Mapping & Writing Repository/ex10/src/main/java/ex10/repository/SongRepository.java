package ex10.repository;

import ex10.domain.Song;
import ex10.repository.querydsl.QuerydslSongRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer>, QuerydslSongRepository {
}
