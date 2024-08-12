package ex03.domain;

import ex03.domain.type.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders", schema = "bookmall")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @Column(name = "number", nullable = false, length = 20)
    private String number;

    @Column(name = "payment", nullable = false)
    private Integer payment;

    @Column(name = "shipping", nullable = false, length = 200)
    private String shipping;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no")
    private User user;

    public void setUser(User user) {
        this.user = user;

        if (!user.getOrders().contains(this)) {
            user.getOrders().add(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", payment=" + payment +
                ", shipping='" + shipping + '\'' +
                ", status=" + status +
                "}";
    }
}