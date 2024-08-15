package ex10.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NonNull
    @Column(name = "abbr_name", nullable = false, length = 10)
    private String abbrName;

    @ManyToMany(mappedBy = "genres")
    private Set<Song> songs = new HashSet<>();

    public void addSong(Song song) {
        songs.add(song);
        song.getGenres().add(this);
    }

    public void removeSong(Song song){
        song.getGenres().remove(this);
        songs.remove(this);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbrName='" + abbrName + '\'' +
                // for Testing: Fetch Mode LAZY
                // ", songs=" + songs +
                '}';
    }
}