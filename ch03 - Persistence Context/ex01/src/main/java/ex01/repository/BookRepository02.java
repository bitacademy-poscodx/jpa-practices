package ex01.repository;

import ex01.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Objects;

@Slf4j
@Repository
public class BookRepository02 {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public void successSave(Book book) {
        log.info("BookRepository02.successSave book: {}", book);

        EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(emf);
        EntityManager em = Objects.requireNonNull(emHolder).getEntityManager();
        em.persist(book);
    }

    public void failSave(Book book) {
        log.info("BookRepository02.failSave book: {}", book);

        EntityManager em = emf.createEntityManager();
        em.persist(book);
    }

}