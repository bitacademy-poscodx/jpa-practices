package ex01.domain;

import ex01.repository.BookRepository02;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTransactionBoundary02 {
    private static final Book bookMock01 = new Book("book01", "Mastering JPA I");
    private static final Book bookMock02 = new Book("book02", "Mastering JPA II");

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private BookRepository02 bookRepository;

    @Test
    @Order(0)
    void beforeAllTest() {
        EntityManager em = emf.createEntityManager();
        TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        log.info("----------- Began Transaction: {}, active: {}", tx, tx.isActive());

        bookRepository.successSave(bookMock01);
        bookRepository.failSave(bookMock02);

        tx.commit();
        log.info("----------- Committed Transaction: {}, active: {}", tx, tx.isActive());
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