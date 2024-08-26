package bookmall.repository;

import bookmall.domain.Book;
import bookmall.domain.EBook;
import bookmall.domain.Goods;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrdersRepository {
    private static final Goods goodsMock01 = new Book(1, "책01", 1000, "저자01", "isbn01");
    private static final Goods goodsMock02 = new Book(2, "책02", 2000, "저자02", "isbn02");
    private static final Goods goodsMock03 = new Book(3, "책03", 3000, "저자03", "isbn03");

    private static final Goods goodsMock04 = new EBook(1, "전자책01", 500, "저자01", "isbn04");
    private static final Goods goodsMock05 = new EBook(2, "전자책02", 1000, "저자02", "isbn05");
    private static final Goods goodsMock06 = new EBook(3, "전자책03", 1500, "저자03", "isbn06");

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testSave() {
        assertThat(goodsRepository.save(goodsMock01).getId()).isNotNull();
        assertThat(goodsRepository.save(goodsMock02).getId()).isNotNull();
        assertThat(goodsRepository.save(goodsMock03).getId()).isNotNull();
        assertThat(goodsRepository.save(goodsMock04).getId()).isNotNull();
        assertThat(goodsRepository.save(goodsMock05).getId()).isNotNull();
        assertThat(goodsRepository.save(goodsMock06).getId()).isNotNull();

    }
}
