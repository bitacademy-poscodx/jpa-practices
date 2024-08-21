package ex01.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={PersistenceConfig01.class})
class TestPersistenceConfig01 {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private TransactionManager tm;

    @Test
    public void testPersistenceConfiguration() {
        log.info("EntityManagerFactory: {}", emf);
        log.info("TransactionManager: {}", tm);
    }
}