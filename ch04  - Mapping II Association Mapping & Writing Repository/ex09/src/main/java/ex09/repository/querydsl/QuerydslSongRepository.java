package ex09.repository.querydsl;

import ex09.domain.Song;

import java.util.List;

public interface QuerydslSongRepository {
    Song findById02(Integer id);
    List<Song> findAll02();
    void deleteGenreByIdAndGenreId(Integer id, Integer genreId);
}
