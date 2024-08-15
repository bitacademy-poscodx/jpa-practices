package ex10.repository;

import ex10.domain.Genre;
import ex10.domain.Song;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSongRepository {
    private static Genre[] genreMocks = new Genre[] {new Genre("쟝르1", "JR1"), new Genre("쟝르2", "JR2"), new Genre("쟝르3", "JR3"), new Genre("쟝르4", "JR4")};
    private static Song[] songMocks = new Song[] {new Song("노래1"), new Song("노래2")};
    private static Long countAllSongs;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave() {
        genreRepository.save(genreMocks[0]);
        assertNotNull(genreMocks[0].getId());

        genreRepository.save(genreMocks[1]);
        assertNotNull(genreMocks[1].getId());

        genreRepository.save(genreMocks[2]);
        assertNotNull(genreMocks[2].getId());

        genreRepository.save(genreMocks[3]);
        assertNotNull(genreMocks[3].getId());

        songMocks[0].getGenres().add(genreMocks[0]);
        songMocks[0].getGenres().add(genreMocks[1]);
        songRepository.save(songMocks[0]);
        assertNotNull(songMocks[0].getId());

        songMocks[1].getGenres().add(genreMocks[2]);
        songMocks[1].getGenres().add(genreMocks[3]);
        songRepository.save(songMocks[1]);
        assertNotNull(songMocks[1].getId());

        countAllSongs = songRepository.count();
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById() {
        Song song = songRepository.findById(songMocks[0].getId()).get();

        assertFalse(emf.getPersistenceUnitUtil().isLoaded(song.getGenres()));
        assertEquals(2, song.getGenres().size());
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(song.getGenres()));
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById02() {
        Song song = songRepository.findById02(songMocks[1].getId());
        assertEquals(2, song.getGenres().size());
    }

    @Test
    @Order(3)
    @Transactional  // for Divisioning JPQL Logs
    public void testFindAll() {
        List<Song> songs = songRepository.findAll();
        assertEquals(countAllSongs, songs.size());

        List<Genre> genres = songs.get(0).getGenres();
        assertFalse(emf.getPersistenceUnitUtil().isLoaded(genres));

        assertEquals(2, genres.size());
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(genres));
    }

    @Test
    @Order(4)
    @Transactional  // for Divisioning JPQL Logs
    public void testFindAll02() {
        List<Song> songs = songRepository.findAll02();
        assertEquals(countAllSongs, songs.size());

        List<Genre> genres = songs.get(0).getGenres();
        assertEquals(2, genres.size());
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(genres));
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(false)
    public void testDeleteGenre(){
        Song song = songRepository.findById(songMocks[0].getId()).get();
        List<Genre> genres = song.getGenres();

        Genre genre = genreRepository.findById(genreMocks[0].getId()).get();

        assertTrue(genres.remove(genre));
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(false)
    public void testDeleteGenreByIdAndGenreId(){
        songRepository.deleteGenreByIdAndGenreId(songMocks[0].getId(), genreMocks[1].getId());
        songRepository.deleteGenreByIdAndGenreId(songMocks[1].getId(), genreMocks[2].getId());
        songRepository.deleteGenreByIdAndGenreId(songMocks[1].getId(), genreMocks[3].getId());

        Song song = songRepository.findById(songMocks[0].getId()).get();
        assertEquals(0, song.getGenres().size());
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp() throws Throwable {
        genreRepository.deleteAllById(List.of(genreMocks[0].getId(), genreMocks[1].getId(), genreMocks[2].getId(), genreMocks[3].getId()));
        songRepository.deleteAllById(List.of(songMocks[0].getId(), songMocks[1].getId()));
    }
}