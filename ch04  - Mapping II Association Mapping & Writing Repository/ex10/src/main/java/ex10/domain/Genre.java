package ex10.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
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
}