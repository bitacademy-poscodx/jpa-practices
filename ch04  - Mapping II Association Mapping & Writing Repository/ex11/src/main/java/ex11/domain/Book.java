package ex11.domain;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @NonNull
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "price", nullable = false)
    private Integer price = 10;
}
