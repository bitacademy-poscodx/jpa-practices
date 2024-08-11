package ex02.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "board", schema = "webdb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NonNull
    @Type(type = "text")
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "hit", nullable = false)
    private Integer hit = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date", nullable = false)
    private Date regDate = new Date();

    @Column(name = "g_no", nullable = false)
    private Integer groupNo = 0;

    @Column(name = "o_no", nullable = false)
    private Integer orderNo = 0;

    @Column(name = "depth", nullable = false)
    private Integer depth = 0;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;
}