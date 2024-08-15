package ex03.repository;

import ex03.domain.User;
import ex03.repository.querydsl.QuerydslUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, QuerydslUserRepository {
    User findByEmailAndPassword(String email, String password);
    User findOrdersById(Integer id);
}
