package ex03.repository;


import ex03.domain.Orders;
import ex03.domain.User;
import ex03.domain.dto.OrdersCountOfUserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrderRepository {
    private static final User[] users = new User[]{new User("둘리"), new User("마이콜"), new User("또치")};
    private static final Orders[] orders = new Orders[]{new Orders("order01"), new Orders("order02"), new Orders("order03"), new Orders("order04"), new Orders("order05"), new Orders("order06")};

    private static Long countOrder;
    private static Long countUsers;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdersRepository orderRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void test01Save(){
        userRepository.save(users[0]);
        assertNotNull(users[0].getId());

        orders[0].setUser(users[0]);
        orderRepository.save(orders[0]);
        assertNotNull(orders[0].getId());

        orders[1].setUser(users[0]);
        orderRepository.save(orders[1]);
        assertNotNull(orders[1].getId());

        orders[2].setUser(users[0]);
        orderRepository.save(orders[2]);
        assertNotNull(orders[2].getId());

        //================================

        userRepository.save(users[1]);
        assertNotNull(users[1].getId());

        orders[3].setUser(users[1]);
        orderRepository.save(orders[3]);
        assertNotNull(orders[3].getId());

        orders[4].setUser(users[1]);
        orderRepository.save(orders[4]);
        assertNotNull(orders[4].getId());

        //================================

        userRepository.save(users[2]);
        assertNotNull(users[2].getId());

        orders[5].setUser(users[2]);
        orderRepository.save(orders[5]);
        assertNotNull(orders[5].getId());

        //================================

        countOrder = orderRepository.count();
        countUsers = userRepository.count();
    }

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testUpdateUser01(){
        User user03 = userRepository.findById(users[2].getId()).get();
        Orders order01 = orderRepository.findById(orders[0].getId()).get();

        user03.getOrders().add(order01);

        assertNotEquals(2, orderRepository.findAllByUserId(user03.getId()).size());
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback(false)
    public void testUpdateUser02(){
        User user03 = userRepository.findById(users[2].getId()).get();
        Orders order01 = orderRepository.findById(orders[0].getId()).get();

        order01.setUser(user03);

        assertEquals(2, orderRepository.findAllByUserId(user03.getId()).size());
    }


    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId_01() {
        List<Orders> list = orderRepository.findAllByUserId(users[0].getId());

        assertTrue(emf.getPersistenceUnitUtil().isLoaded(list));
        assertEquals(orderRepository.countAllByUserId(users[0].getId()), list.size());
    }

    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId_02() {
        List<Orders> list = orderRepository.findAllByUserId(users[0].getId(), Sort.by(Sort.Direction.DESC, "number"));

        assertTrue(emf.getPersistenceUnitUtil().isLoaded(list));
        assertEquals(orderRepository.countAllByUserId(users[0].getId()), list.size());
    }

    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId02_01() {
        List<Orders> list = orderRepository.findAllByUserId02(users[0].getId());

        assertTrue(emf.getPersistenceUnitUtil().isLoaded(list));
        assertEquals(orderRepository.countAllByUserId(users[0].getId()), list.size());
    }

    @Test
    @Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId02_02() {
        List<Orders> list = orderRepository.findAllByUserId02(users[0].getId(), Sort.by(Sort.Direction.DESC, "payment").and(Sort.by(Sort.Direction.DESC, "number")));

        assertTrue(emf.getPersistenceUnitUtil().isLoaded(list));
        assertEquals(orderRepository.countAllByUserId(users[0].getId()), list.size());
    }

    @Test
    @Order(7)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId02_03() {
        List<Orders> list = orderRepository.findAllByUserId02(users[0].getId(), PageRequest.of(0, 2, Sort.Direction.DESC, "number"));
        assertEquals(2, list.size());
    }

    @Test
    @Order(8)
    @Transactional // for Divisioning JPQL Logs
    public void testCountAllGroupByUser() {
        Long totalOrderCount = 0L;
        final Long totalOrderCountExpected = orderRepository.count();

        List<OrdersCountOfUserDto> list = orderRepository.countAllGroupByUser();
        for(OrdersCountOfUserDto dto: list){
            totalOrderCount += dto.getOrderCount();
        }

        assertTrue(emf.getPersistenceUnitUtil().isLoaded(list));
        assertEquals(totalOrderCountExpected, totalOrderCount);
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp() {
        for (Orders order : orders) {
            orderRepository.deleteById(order.getId());
        }

        for (User user : users) {
            userRepository.deleteById(user.getId());
        }
    }
}