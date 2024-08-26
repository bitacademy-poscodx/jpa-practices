package bookmall.repository;

import bookmall.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBookRepository {
    private final Book bookMock01 = new Book(1, "책01", 1000, "저자01", "isbn01");

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    public void testSave() {
        assertThat(bookRepository.save(bookMock01).getId()).isNotNull();
    }
}
