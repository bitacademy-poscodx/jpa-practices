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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpqlGuestbookRepository {
    private static final Guestbook guesbookMock01 = new Guestbook("고길동", "1234", "안녕");
    private static final Guestbook guesbookMock02 = new Guestbook("고길동", "1234", "안녕");
    private static Long countGuestbook = 0L;

    @Autowired
    private PersistGuestbookRepository psersistRepository;

    @Autowired
    private JpqlGuestbookRepository jpqlRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testSave() throws InterruptedException {
        psersistRepository.save(guesbookMock01);
        Thread.sleep(2000);
        psersistRepository.save(guesbookMock02);

        assertNotNull(guesbookMock01.getId());
        assertNotNull(guesbookMock02.getId());

        countGuestbook = jpqlRepository.count();
    }

    @Test
    @Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById() {
        Guestbook guestbook = jpqlRepository.findById(guesbookMock01.getId()).orElse(null);

        assertNotNull(guestbook);
        assertTrue(em.contains(guestbook));
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindByIdWithProjection() {
        GuestbookDto guestbookDto = jpqlRepository.findById(guesbookMock01.getId(), GuestbookDto.class).orElse(null);
        assertNotNull(guestbookDto);
    }


    @Test
    @Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAll() {
        List<Guestbook> list = jpqlRepository.findAll();
        assertEquals(countGuestbook, list.size());
    }

    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllWithProjection() {
        List<GuestbookDto> list = jpqlRepository.findAll(GuestbookDto.class);
        assertEquals(countGuestbook, list.size());
        assertEquals(GuestbookDto.class, list.get(0).getClass());
    }

    @Test
    @Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllOrderByRegDateDesc() {
        List<Guestbook> list = jpqlRepository.findAllOrderByRegDateDesc();
        assertTrue(list.get(0).getRegDate().after(list.get(1).getRegDate()));
    }

    @Test
    @Order(98)
    @Transactional
    @Rollback(false)
    public void testUpdate() {
        Guestbook argGuestbook = new Guestbook();
        argGuestbook.setId(guesbookMock01.getId());
        argGuestbook.setContents("안녕2");
        argGuestbook.setName("고길동2");

        jpqlRepository.update(argGuestbook);
    }

    @Test
    @Order(99)
    @Transactional
    @Rollback(false)
    public void testDeleteBy() {
        int count = jpqlRepository.deleteById(guesbookMock01.getId());
        assertEquals(1, count);
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void testDeleteByIdAndPassword() {
        int count = jpqlRepository.deleteByIdAndPassword(guesbookMock02.getId(), "1234");
        assertEquals(1, count);
    }
}