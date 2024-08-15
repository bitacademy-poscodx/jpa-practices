package ex11.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex11.domain.CartItem;
import ex11.domain.dto.CartItemDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static ex11.domain.QCartItem.cartItem;

public class QuerydslCartItemRepositoryImpl extends QuerydslRepositorySupport implements QuerydslCartItemRepository {

    private final JPAQueryFactory queryFactory;

    public QuerydslCartItemRepositoryImpl(JPAQueryFactory queryFactory) {
        super(CartItem.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CartItem> findAllByUserId02(Integer userId) {
        return  queryFactory
                .select(cartItem)
                .from(cartItem)
                .innerJoin(cartItem.user())
                .fetchJoin()
                .innerJoin(cartItem.book())
                .fetchJoin()
                .where(cartItem.user().id.eq(userId))
                .fetch();
    }

    @Override
    public List<CartItemDto> findAllByUserId03(Integer userId) {
        return  queryFactory
                .select(Projections.constructor(CartItemDto.class, cartItem.book().id, cartItem.book().title, cartItem.book().price, cartItem.quantity))
                .from(cartItem)
                .innerJoin(cartItem.user())
                .innerJoin(cartItem.book())
                .where(cartItem.user().id.eq(userId))
                .fetch();
    }

    @Override
    public void deleteByUserIdAndBookId02(Integer userId, Integer bookId) {
        queryFactory
                .delete(cartItem)
                .where(cartItem.user().id.eq(userId).and(cartItem.book().id.eq(bookId)))
                .execute();
    }
}
