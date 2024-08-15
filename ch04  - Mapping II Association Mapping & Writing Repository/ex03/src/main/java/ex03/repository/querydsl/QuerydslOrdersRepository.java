package ex03.repository.querydsl;

import ex03.domain.Orders;
import ex03.domain.dto.OrdersCountOfUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuerydslOrdersRepository {
    List<Orders> findAllByUserId02(Integer userId);
    List<Orders> findAllByUserId02(Integer userId, Sort sort);
    List<Orders> findAllByUserId02(Integer userId, Pageable pageable);

    List<OrdersCountOfUserDto> countAllGroupByUser();
}
