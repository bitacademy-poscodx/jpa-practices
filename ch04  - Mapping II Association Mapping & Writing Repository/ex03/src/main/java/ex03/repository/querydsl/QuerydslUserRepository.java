package ex03.repository.querydsl;

import ex03.domain.Orders;
import ex03.domain.User;
import ex03.domain.dto.UserDto;

import java.util.List;

public interface QuerydslUserRepository {
    UserDto findById02(Integer id);
    Long update(User user);

    List<Orders> findOrdersById02(Integer id);

    List<User> findAllCollectionJoinProblem();
    List<User> findAllCollectionJoinProblemSolved();
    List<User> findAllCollectionJoinAndNplusOneProblemSolved();
}
