package ex03.repository;

import ex03.domain.Orders;
import ex03.repository.querydsl.QuerydslOrdersRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer>, QuerydslOrdersRepository {
	List<Orders> findAllByUserId(Integer userId);
	List<Orders> findAllByUserId(Integer userId, Sort sort);
	Long countAllByUserId(Integer userId);
}
