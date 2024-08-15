package ex11.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "length", nullable = true)
    private Time length;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "song_genre", joinColumns = @JoinColumn(name = "song_no"), inverseJoinColumns = @JoinColumn(name = "genre_no"))
    private Set<Genre> genres = new HashSet<>();

    public void addGenre(Genre genre){
        genres.add(genre);
        genre.getSongs().add(this);
    }

    public void removeGenre(Genre genre){
        genres.remove(genre);
        genre.getSongs().remove(this);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", length=" + length +
                // for Testing: Fetch Mode LAZY
                // ", genres=" + genres +
                '}';
    }
}