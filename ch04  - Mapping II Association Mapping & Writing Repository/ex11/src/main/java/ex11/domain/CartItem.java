package ex11.domain;

import ex11.domain.identifier.CartItemId;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "cart")
public class CartItem {
    @EmbeddedId
    private CartItemId catrtItemId = new CartItemId();

    @NonNull
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_no")
    private User user;

    @NonNull
    @MapsId("bookId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="book_no")
    private Book book;

    @NonNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
