package bookmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<Goods> goodsList = new ArrayList<>();

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                '}';
    }
}