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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGoodsRepository {
    private static final Goods goodsMock01 = new Book(1, "책01", 1000, "저자01", "isbn01");
    private static final Goods goodsMock02 = new EBook(1, "전자책01", 500, "저자01", "isbn02");
    private static Long countOfGoods = null;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testSave() {
        assertThat(goodsRepository.save(goodsMock01).getId()).isNotNull();
        assertThat(goodsRepository.save(goodsMock02).getId()).isNotNull();

        log.info("<<<Goods Persisted: {}>>>", goodsMock01);
        log.info("<<<Goods Persisted: {}>>>", goodsMock02);

        countOfGoods = goodsRepository.count();
    }

    @Test
    @Order(2)
    public void testFindById() {
        Goods goods = goodsRepository.findById(goodsMock01.getId()).orElse(null);
        assertNotNull(goods);
    }


    @Test
    @Order(3)
    public void testFindAll() {
        List<Goods> list = goodsRepository.findAll();
        assertThat(list.size()).isEqualTo(countOfGoods.intValue());
        log.info("<<<Goods List: {}>>>", list);
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void testDelete() {
        goodsRepository.deleteById(goodsMock01.getId());
        goodsRepository.deleteById(goodsMock02.getId());
    }
}
