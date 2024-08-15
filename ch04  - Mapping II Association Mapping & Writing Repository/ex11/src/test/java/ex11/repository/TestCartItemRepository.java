package ex11.repository;

import ex11.domain.Book;
import ex11.domain.CartItem;
import ex11.domain.User;
import ex11.domain.dto.CartItemDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCartItemRepository {
    private static final User[] userMocks = new User[]{new User("둘리"), new User("마이콜")};
    private static final Book[] bookMocks = new Book[]{new Book("책1"), new Book("책2"), new Book("책3")};
    private static Long countCartItems;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave() {
        userRepository.save(userMocks[0]);
        assertNotNull(userMocks[0].getId());

        userRepository.save(userMocks[1]);
        assertNotNull(userMocks[1].getId());

        bookRepository.save(bookMocks[0]);
        assertNotNull(bookMocks[0].getId());

        bookRepository.save(bookMocks[1]);
        assertNotNull(bookMocks[1].getId());

        bookRepository.save(bookMocks[2]);
        assertNotNull(bookMocks[2].getId());

        cartItemRepository.save(new CartItem(userMocks[0], bookMocks[0], 1));
        cartItemRepository.save(new CartItem(userMocks[0], bookMocks[1], 2));
        cartItemRepository.save(new CartItem(userMocks[1], bookMocks[0], 1));
        cartItemRepository.save(new CartItem(userMocks[1], bookMocks[1], 2));
        cartItemRepository.save(new CartItem(userMocks[1], bookMocks[2], 3));

        countCartItems = cartItemRepository.count();
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId(){
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userMocks[0].getId());

        assertEquals(2, cartItems.size());
        assertEquals("둘리", cartItems.get(0).getUser().getName());
        assertEquals("책1", cartItems.get(0).getBook().getTitle());
        assertEquals("책2", cartItems.get(1).getBook().getTitle());
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId02(){
        List<CartItem> cartItems = cartItemRepository.findAllByUserId02(userMocks[1].getId());

        assertEquals(3, cartItems.size());
        assertEquals("마이콜", cartItems.get(0).getUser().getName());
        assertEquals("책1", cartItems.get(0).getBook().getTitle());
        assertEquals("책2", cartItems.get(1).getBook().getTitle());
        assertEquals("책3", cartItems.get(2).getBook().getTitle());
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllByUserId03(){
        List<CartItemDto> cartItems = cartItemRepository.findAllByUserId03(userMocks[0].getId());

        assertEquals(2, cartItems.size());
        assertEquals("책1", cartItems.get(0).getBookTitle());
        assertEquals("책2", cartItems.get(1).getBookTitle());
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(false)
    public void testDeleteByUserIdAndBookId(){
        cartItemRepository.deleteByUserIdAndBookId(userMocks[0].getId(), bookMocks[0].getId());
        assertEquals(--countCartItems, cartItemRepository.count());
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(false)
    public void testDeleteByUserIdAndBookId02(){
        cartItemRepository.deleteByUserIdAndBookId02(userMocks[0].getId(), bookMocks[1].getId());
        assertEquals(--countCartItems, cartItemRepository.count());
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp(){
        cartItemRepository.deleteByUserIdAndBookId02(userMocks[1].getId(), bookMocks[0].getId());
        cartItemRepository.deleteByUserIdAndBookId02(userMocks[1].getId(), bookMocks[1].getId());
        cartItemRepository.deleteByUserIdAndBookId02(userMocks[1].getId(), bookMocks[2].getId());

        userRepository.deleteAllById(List.of(userMocks[0].getId(), userMocks[1].getId()));
        bookRepository.deleteAllById(List.of(bookMocks[0].getId(), bookMocks[1].getId(), bookMocks[2].getId()));
    }
}