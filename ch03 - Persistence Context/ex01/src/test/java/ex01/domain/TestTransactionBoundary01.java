package ex01.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTransactionBoundary01 {
    private static final Book bookMock01 = new Book("book01", "Mastering JPA");
    private static final Book bookMock02 = new Book("book02", "Mastering JPA");

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    @Order(0)
    void beforeAllTest() {

        // 1. in EntityTransaction Boundary
        EntityManager em01 = emf.createEntityManager();
        EntityTransaction tx = em01.getTransaction();

        tx.begin();
        log.info("----------- Began Transaction: {}, active: {}", tx, tx.isActive());

        em01.persist(bookMock01);

        tx.commit();
        log.info("----------- Committed Transaction: {}, active: {}", tx, tx.isActive());


        // 2. Not in EntityTransaction Boundary
        Book book02 = new Book("book02", "Mastering JPA");
        EntityManager em02 = emf.createEntityManager();

        em02.persist(bookMock02);
    }

    @Test
    @Order(1)
    void testInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertThat(em.find(Book.class, "book01")).isNotNull();
    }

    @Test
    @Order(2)
    void testNotInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertThat(em.find(Book.class, "book02")).isNull();
    }
}