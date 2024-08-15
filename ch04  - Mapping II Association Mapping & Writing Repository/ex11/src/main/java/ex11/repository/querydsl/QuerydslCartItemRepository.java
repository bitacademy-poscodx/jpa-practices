package ex11.repository.querydsl;

import ex11.domain.CartItem;
import ex11.domain.dto.CartItemDto;

import java.util.List;

public interface QuerydslCartItemRepository {
    List<CartItem> findAllByUserId02(Integer userId);
    List<CartItemDto> findAllByUserId03(Integer userId);
    void deleteByUserIdAndBookId02(Integer userId, Integer bookId);
}
