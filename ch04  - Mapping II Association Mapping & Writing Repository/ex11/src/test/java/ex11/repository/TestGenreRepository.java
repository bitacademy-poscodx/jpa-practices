package ex11.repository;

import ex11.domain.Genre;
import ex11.domain.Song;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGenreRepository {
    private static Genre[] genreMocks = new Genre[] {new Genre("쟝르1", "JR1"), new Genre("쟝르2", "JR2"), new Genre("쟝르3", "JR3"), new Genre("쟝르4", "JR4")};
    private static Song[] songMocks = new Song[] {new Song("노래1"), new Song("노래2")};
    private static Long countAllGenres;

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
        songRepository.save(songMocks[0]);
        assertNotNull(songMocks[0].getId());

        songRepository.save(songMocks[1]);
        assertNotNull(songMocks[1].getId());

        genreMocks[0].addSong(songMocks[0]);
        genreMocks[0].addSong(songMocks[1]);
        genreRepository.save(genreMocks[0]);
        assertNotNull(genreMocks[0].getId());

        genreMocks[1].addSong(songMocks[0]);
        genreRepository.save(genreMocks[1]);
        assertNotNull(genreMocks[1].getId());

        genreMocks[2].addSong(songMocks[1]);
        genreRepository.save(genreMocks[2]);
        assertNotNull(genreMocks[2].getId());

        genreRepository.save(genreMocks[3]);
        assertNotNull(genreMocks[3].getId());

        countAllGenres = genreRepository.count();
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById() {
        Genre genre = genreRepository.findById(genreMocks[0].getId()).get();

        assertFalse(emf.getPersistenceUnitUtil().isLoaded(genre.getSongs()));
        assertEquals(2, genre.getSongs().size());
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(genre.getSongs()));
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById02() {
        Genre genre = genreRepository.findById02(genreMocks[1].getId());
        assertEquals(1, genre.getSongs().size());
    }

    @Test
    @Order(3)
    @Transactional  // for Divisioning JPQL Logs
    public void testFindAll() {
        List<Genre> genres = genreRepository.findAll();
        assertEquals(countAllGenres, genres.size());

        Set<Song> songs = genres.get(0).getSongs();
        assertFalse(emf.getPersistenceUnitUtil().isLoaded(songs));

        assertEquals(2, songs.size());
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(songs));
    }

    @Test
    @Order(4)
    @Transactional  // for Divisioning JPQL Logs
    public void testFindAll02() {
        List<Genre> genres = genreRepository.findAll02();
        assertEquals(countAllGenres, genres.size());

        Set<Song> songs = genres.get(0).getSongs();
        assertEquals(2, songs.size());
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(songs));
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(false)
    public void testDeleteSong(){
        Genre genre = genreRepository.findById(genreMocks[0].getId()).get();
        Song song = songRepository.findById(songMocks[0].getId()).get();

        genre.removeSong(song);

        Set<Genre> genres = song.getGenres();
        assertEquals(1, genres.size());
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp() throws Throwable {
        Song song01 = songRepository.findById(songMocks[0].getId()).get();
        Song song02 = songRepository.findById(songMocks[1].getId()).get();

        Genre genre01 = genreRepository.findById(genreMocks[0].getId()).get();
        genre01.removeSong(song02);

        Genre genre02 = genreRepository.findById(genreMocks[1].getId()).get();
        genre02.removeSong(song01);

        Genre genre03 = genreRepository.findById(genreMocks[2].getId()).get();
        genre03.removeSong(song02);

        genreRepository.deleteAllById(List.of(genreMocks[0].getId(), genreMocks[1].getId(), genreMocks[2].getId(), genreMocks[3].getId()));
        songRepository.deleteAllById(List.of(songMocks[0].getId(), songMocks[1].getId()));
    }
}