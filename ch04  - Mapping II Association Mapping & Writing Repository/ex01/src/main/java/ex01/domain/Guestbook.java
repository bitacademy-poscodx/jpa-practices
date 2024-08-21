package ex01.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guestbook", schema = "webdb")
public class Guestbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NonNull
    @Column(name = "password", nullable = false, length = 10)
    private String password;

    @NonNull
    @Type(type = "text")
    @Column(name = "contents", nullable = false)
    private String contents;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date", nullable = false)
    private Date regDate;
}