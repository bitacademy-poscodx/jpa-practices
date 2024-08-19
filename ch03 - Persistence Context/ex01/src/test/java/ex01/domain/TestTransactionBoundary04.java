package ex01.domain;

import ex01.repository.BookRepository04;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTransactionBoundary04 {
    private static final Book bookMock01 = new Book("book01", "Mastering JPA I");

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    BookRepository04 bookRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    void beforeAllTest() {
        bookRepository.save(bookMock01);
    }

    @Test
    @Order(1)
    void testInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertNotNull(em.find(Book.class, "book01"));
    }
}