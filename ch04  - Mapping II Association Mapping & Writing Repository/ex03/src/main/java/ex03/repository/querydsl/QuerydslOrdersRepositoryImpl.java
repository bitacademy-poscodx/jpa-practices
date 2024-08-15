package ex03.repository.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex03.domain.Orders;
import ex03.domain.dto.OrdersCountOfUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static ex03.domain.QOrders.orders;

public class QuerydslOrdersRepositoryImpl extends QuerydslRepositorySupport implements QuerydslOrdersRepository {
    private final JPAQueryFactory queryFactory;

    public QuerydslOrdersRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Orders.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Orders> findAllByUserId02(Integer userId) {
        return queryFactory
                .select(orders)
                .from(orders)
                .innerJoin(orders.user())
                .fetchJoin()
                .where(orders.user().id.eq(userId))
                .fetch();
    }

    @Override
    public List<Orders> findAllByUserId02(Integer userId, Sort sort) {
        JPAQuery<Orders> query = queryFactory
                .select(orders)
                .from(orders)
                .innerJoin(orders.user())
                .fetchJoin()
                .where(orders.user().id.eq(userId));

        for (Sort.Order o : sort) {

            PathBuilder<Orders> ordersByExpression = new PathBuilder<>(Orders.class, "orders");
            query.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, ordersByExpression.get(o.getProperty())));
        }

        return query.fetch();
    }

    @Override
    public List<Orders> findAllByUserId02(Integer userId, Pageable pageable) {
        JPAQuery<Orders> query = queryFactory
                .select(orders)
                .from(orders)
                .innerJoin(orders.user())
                .fetchJoin()
                .where(orders.user().id.eq(userId));

        if (pageable != null) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());

            for (Sort.Order o : pageable.getSort()) {
                PathBuilder ordersByExpression = new PathBuilder(Orders.class, "orders");
                query.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, ordersByExpression.get(o.getProperty())));
            }
        }

        return query.fetch();
    }

    @Override
    public List<OrdersCountOfUserDto> countAllGroupByUser(){
        return queryFactory
                .select(Projections.constructor(OrdersCountOfUserDto.class, orders.user().id, orders.user().id.count()))
                .from(orders)
                .innerJoin(orders.user())
                .groupBy(orders.user())
                .fetch();
    }
}