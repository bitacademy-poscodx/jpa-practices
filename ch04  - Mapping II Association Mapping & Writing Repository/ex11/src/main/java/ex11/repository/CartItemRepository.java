package ex11.repository;

import ex11.domain.CartItem;
import ex11.domain.identifier.CartItemId;
import ex11.repository.querydsl.QuerydslCartItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId>, QuerydslCartItemRepository {
    List<CartItem> findAllByUserId(Integer userId);

    void deleteByUserIdAndBookId(Integer userId, Integer bookId);
}
