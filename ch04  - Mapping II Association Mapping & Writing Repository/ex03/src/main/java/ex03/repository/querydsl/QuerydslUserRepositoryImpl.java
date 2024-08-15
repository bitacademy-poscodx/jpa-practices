package ex03.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex03.domain.Orders;
import ex03.domain.User;
import ex03.domain.dto.UserDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static ex03.domain.QUser.user;

public class QuerydslUserRepositoryImpl extends QuerydslRepositorySupport implements QuerydslUserRepository {

    private final JPAQueryFactory queryFactory;

    public QuerydslUserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public UserDto findById02(Integer id) {
        return queryFactory
                .select(Projections.bean(UserDto.class, user.id, user.name))
                .from(user)
                .where(user.id.eq(id))
                .fetchOne();
    }

    @Override
    public Long update(User argUser) {
        return queryFactory
                .update(user)
                .where(user.id.eq(argUser.getId()))
                .set(user.name, argUser.getName())
                .set(user.email, argUser.getEmail())
                .set(user.password, argUser.getPassword())
                .set(user.phone, argUser.getPhone())
                .execute();
    }

    @Override
    public List<User> findAllCollectionJoinProblem() {
        return queryFactory
                .select(user)
                .from(user)
                .innerJoin(user.orders)
                .fetch();
    }

    public List<User> findAllCollectionJoinProblemSolved() {
        return queryFactory
                .selectDistinct(user)
                .from(user)
                .innerJoin(user.orders)
                .fetch();
    }

    public List<User> findAllCollectionJoinAndNplusOneProblemSolved() {
        return queryFactory
                .selectDistinct(user)
                .from(user)
                .innerJoin(user.orders)
                .fetchJoin()
                .fetch();
    }

    @Override
    public List<Orders> findOrdersById02(Integer id) {
        return queryFactory
                .selectDistinct(user)
                .from(user)
                .innerJoin(user.orders)
                .fetchJoin()
                .where(user.id.eq(id))
                .fetchOne()
                .getOrders();
    }
}
