package ex01.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "book", schema = "jpadb")
public class Book {
    @NotNull
    @Id
    @Column(nullable = false)
    private String id;

    @NotNull
    @Column(nullable = false, length = 200)
    private String title;
}