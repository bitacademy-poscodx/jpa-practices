package ex11.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user", schema = "bookmall")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 200)
    private String email = "xx@xxx.xxx";

    @Column(name = "password", nullable = false, length = 64)
    private String password = "####";

    @Column(name = "phone", nullable = false, columnDefinition="char(13)")
    private String phone = "000-0000-0000";

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                // escape infinite-loop
                // ", cartItems=" + cartItems +
                '}';
    }
}