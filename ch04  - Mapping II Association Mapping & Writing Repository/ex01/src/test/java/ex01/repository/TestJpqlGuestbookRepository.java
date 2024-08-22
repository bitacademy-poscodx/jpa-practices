package ex01.repository;

import ex01.domain.Guestbook;
import ex01.domain.dto.GuestbookDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpqlGuestbookRepository {
    private static final Guestbook guesbookMock = new Guestbook("고길동", "1234", "안녕");

    @Autowired
    private PersistGuestbookRepository psersistRepository;

    @Autowired
    private JpqlGuestbookRepository jpqlRepository;


    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave() {
        psersistRepository.save(guesbookMock);
        assertNotNull(guesbookMock.getId());
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAll() {
        List<Guestbook> list = jpqlRepository.findAll();
        assertEquals(jpqlRepository.count(), list.size());
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithProjection() {
        List<GuestbookDto> list = jpqlRepository.findAllWithProjection();
        assertEquals(jpqlRepository.count(), list.size());
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

        jpqlRepository.update(argGuestbook);
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(false)
    public void testDeleteByIdAndPassword() {
        Integer count = jpqlRepository.deleteByIdAndPassword(guesbookMock.getId(), "1234");
        assertEquals(1, count);
    }
}