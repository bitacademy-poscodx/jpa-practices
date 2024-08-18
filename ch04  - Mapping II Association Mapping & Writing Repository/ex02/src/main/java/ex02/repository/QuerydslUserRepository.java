package ex02.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.User;
import ex02.domain.dto.UserDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static ex02.domain.QUser.user;

@Repository
public class QuerydslUserRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public QuerydslUserRepository(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public void save(User user) {
        Objects.requireNonNull(getEntityManager()).persist(user);
    }

    public User findById(Integer id) {
        return (User) queryFactory
                .from(user)
                .where(user.id.eq(id))
                .fetchOne();
    }

    public UserDto findByEmailAndPassword(String email, String password) {
        return queryFactory
                .select(Projections.constructor(UserDto.class, user.id, user.name))
                .from(user)
                // .where(user.email.eq(email).and(user.password.eq(password)))
                .where(user.email.eq(email), user.password.eq(password))
                .fetchOne();
    }

    public Long update(User argUser) {
        return queryFactory.update(user)
                .where(user.id.eq(argUser.getId()))
                .set(user.name, argUser.getName())
                .set(user.email, argUser.getEmail())
                .set(user.password, argUser.getPassword())
                .set(user.gender, argUser.getGender())
                .set(user.role, argUser.getRole())
                .execute();
    }

    public Long deleteById(Integer id) {
        return queryFactory
                .delete(user)
                .where(user.id.eq(id))
                .execute();
    }

    public Long count() {
        return queryFactory
                .select(user.count())
                .from(user)
                .fetchOne();
    }
}