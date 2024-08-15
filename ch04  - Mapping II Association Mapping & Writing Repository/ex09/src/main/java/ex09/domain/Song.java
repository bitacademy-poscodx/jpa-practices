package ex09.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
    private List<Genre> genres = new ArrayList<>();

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