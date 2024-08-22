package ex01.repository;

import ex01.domain.Guestbook;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPersisGuestbookRepository {
    private static final Guestbook guesbookMock = new Guestbook("고길동", "1234", "안녕");

    @Autowired
    private PersistGuestbookRepository guestbookRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testSave() {
        guestbookRepository.save(guesbookMock);
        assertThat(guesbookMock.getId()).isNotNull();
    }

    @Test
    @Order(2)
    @Transactional // for divisioning log
    public void testFind() {
        assertThat(guestbookRepository.find(guesbookMock.getId())).isNotNull();
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(false)
    public void testUpdate() {
        Guestbook argGuestbook = new Guestbook();
        argGuestbook.setId(guesbookMock.getId());
        argGuestbook.setContents("안녕2");
        argGuestbook.setName("고길동2");


        Guestbook guestbook = guestbookRepository.update(argGuestbook);
        assertNotNull(guestbook.getId());
        assertThat(guestbookRepository.find(guesbookMock.getId()).map(Guestbook::getContents).orElse(null)).isEqualTo("안녕2");
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(false)
    public void testDelete() {
        guestbookRepository.delete(guesbookMock.getId());
        assertThat(guestbookRepository.find(guesbookMock.getId())).isEmpty();
    }
}