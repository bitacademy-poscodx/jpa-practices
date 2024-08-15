package ex03.repository;

import ex03.domain.Orders;
import ex03.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUerRepository {
    private static final User[] users = new User[]{new User("둘리"), new User("마이콜"), new User("또치")};
    private static final Orders[] orders = new Orders[]{new Orders("order01"), new Orders("order02"), new Orders("order03"), new Orders("order04"), new Orders("order05"), new Orders("order06")};

    private static Long countOrders;
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
    public void testSave() {
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

        countOrders = orderRepository.count();
        countUsers = userRepository.count();
    }

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testUpdate() {
        User user = new User();
        user.setId(users[0].getId());
        user.setEmail("dooly@kickscar.me");
        user.setName("둘리");
        user.setPassword("1234");
        user.setPhone("000-0000-0000");

        assertEquals(1L, userRepository.update(user));
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindOrderById() {
        User user = userRepository.findOrdersById(users[0].getId());
        List<Orders> orders = user.getOrders();

        assertEquals(3, orders.size());
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindOrdersById() {
        User user = userRepository.findById(users[0].getId()).get();
        List<Orders> orders = user.getOrders();

        assertEquals(3, orders.size());
    }

    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllCollectionJoinProblem() {
        List<User> users = userRepository.findAllCollectionJoinProblem();

        for (User user : users) {
            System.out.println(user);
        }

        assertEquals(countOrders, users.size());
    }


    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testCollectionJoinProblemSolved() {
        List<User> users = userRepository.findAllCollectionJoinProblemSolved();

        for (User user : users) {
            System.out.println(user);
        }

        assertEquals(countUsers, users.size());
    }

    @Test
    @Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testNplusOneProblem() {
        Integer qryCount = 0;
        Integer orderCountActual = 0;

        Integer orderCountExpected = countOrders.intValue();
        Integer N = countUsers.intValue();

        List<User> users = userRepository.findAll();
        qryCount++;

        for (User user : users) {
            List<Orders> orders = user.getOrders();
            if (!emf.getPersistenceUnitUtil().isLoaded(orders)) {
                qryCount++;
            }

            orderCountActual += orders.size();
        }

        assertEquals(orderCountExpected, orderCountActual);
        assertEquals(N + 1, qryCount);
    }


    @Test
    @Order(7)
    @Transactional // for Divisioning JPQL Logs
    public void testNplusOneProblemNotSolvedYet() {
        Integer qryCount = 0;
        Integer orderCountActual = 0;

        Integer orderCountExpected = countOrders.intValue();
        Integer N = countUsers.intValue();

        List<User> users = userRepository.findAllCollectionJoinProblemSolved();
        qryCount++;

        for (User user : users) {
            List<Orders> result = user.getOrders();

            if (!emf.getPersistenceUnitUtil().isLoaded(result)) {
                qryCount++;
            }

            orderCountActual += result.size();
        }

        assertEquals(orderCountExpected, orderCountActual);
        assertEquals(N + 1, qryCount);
    }

    @Test
    @Order(8)
    @Transactional // for Divisioning JPQL Logs
    public void testNplusOneProblemSolved() {
        Integer qryCount = 0;
        Integer orderCountActual = 0;

        Integer orderCountExpected = countOrders.intValue();
        Integer N = countUsers.intValue();

        List<User> users = userRepository.findAllCollectionJoinAndNplusOneProblemSolved();
        qryCount++;

        for (User user : users) {
            List<Orders> result = user.getOrders();

            if (!emf.getPersistenceUnitUtil().isLoaded(result)) {
                qryCount++;
            }

            orderCountActual += result.size();
        }

        assertEquals(orderCountExpected, orderCountActual);
        assertEquals(1, qryCount);
    }

    @Test
    @Order(9)
    @Transactional
    @Rollback(false)
    public void testFindOrderByIdFinal() {
        List<Orders> orders = userRepository.findOrdersById02(users[0].getId());
        assertEquals(3, orders.size());
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