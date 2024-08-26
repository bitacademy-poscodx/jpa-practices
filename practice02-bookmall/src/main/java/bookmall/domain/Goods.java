package bookmall.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "goods")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "index", nullable = false)
    private Integer index;

    @NonNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NonNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    public void setOrders(Orders orders) {
        this.orders = orders;

        if (!orders.getGoodsList().contains(this)) {
            orders.getGoodsList().add(this);
        }
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", index='" + index + '\'' +
                ", title='" + title + '\'' +
                ", price="  + price +
                '}';
    }
}