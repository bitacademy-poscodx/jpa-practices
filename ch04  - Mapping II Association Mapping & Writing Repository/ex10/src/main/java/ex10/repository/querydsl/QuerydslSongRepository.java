package ex10.repository.querydsl;

import ex10.domain.Song;

import java.util.List;

public interface QuerydslSongRepository {
    Song findById02(Integer id);
    List<Song> findAll02();
    void deleteGenreByIdAndGenreId(Integer id, Integer genreId);
}
