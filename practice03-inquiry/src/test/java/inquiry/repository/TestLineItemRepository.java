package inquiry.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inquiry.domain.CarLineItem;
import inquiry.domain.ColdRolledLineItem;
import inquiry.domain.Inquiry;
import inquiry.domain.LineItem;
import inquiry.domain.dto.InquiryDto;
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
public class TestLineItemRepository {
    private static final LineItem lineItem11 = CarLineItem.builder().orderNumber(1).partName("partName01").salesVehicleName("salesVehicleName01").build();
    private static final LineItem lineItem12 = CarLineItem.builder().orderNumber(2).partName("partName02").salesVehicleName("salesVehicleName02").build();
    private static final LineItem lineItem13 = CarLineItem.builder().orderNumber(3).partName("partName03").salesVehicleName("salesVehicleName03").build();
    private static final LineItem lineItem14 = CarLineItem.builder().orderNumber(4).partName("partName04").salesVehicleName("salesVehicleName04").build();

    private static final LineItem lineItem21 = ColdRolledLineItem.builder().orderNumber(1).width("1 mm").thickness("1 inch").quantity(1).build();
    private static final LineItem lineItem22 = ColdRolledLineItem.builder().orderNumber(2).width("2 mm").thickness("2 inch").quantity(2).build();
    private static final LineItem lineItem23 = ColdRolledLineItem.builder().orderNumber(3).width("3 mm").thickness("3 inch").quantity(3).build();
    private static final LineItem lineItem24 = ColdRolledLineItem.builder().orderNumber(4).width("4 mm").thickness("4 inch").quantity(4).build();

    private static final Inquiry inquiry1 = Inquiry.builder().corporate("company1").salesPerson("영업사원1").build();
    private static final Inquiry inquiry2 = Inquiry.builder().corporate("company2").salesPerson("영업사원3").build();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testSave() {
        assertThat(inquiryRepository.save(inquiry1).getId()).isNotNull();
        assertThat(inquiryRepository.save(inquiry2).getId()).isNotNull();

        lineItem11.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem11).getId()).isNotNull();

        lineItem12.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem12).getId()).isNotNull();

        lineItem13.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem13).getId()).isNotNull();

        lineItem14.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem14).getId()).isNotNull();


        lineItem21.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem21).getId()).isNotNull();

        lineItem22.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem22).getId()).isNotNull();

        lineItem23.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem23).getId()).isNotNull();

        lineItem24.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem24).getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void testFindById() {
        LineItem lineItem = lineItemRepository.findById(lineItem11.getId()).orElse(null);
        assertNotNull(lineItem);
    }

    @Test
    @Order(3)
    @Transactional
    public void testFindAll02() throws JsonProcessingException {
        List<LineItem> list = lineItemRepository.findAll02();
        log.info("List<LineItem>: {}>>>", objectMapper.writeValueAsString(list));
    }

    @Test
    @Order(4)
    public void testFindAll03() throws JsonProcessingException {
        List<InquiryDto> list = lineItemRepository.findAll03();
        log.info("List<InquiryDto>: {}>>>",objectMapper.writeValueAsString(list));
    }

    @Test
    @Order(100)
    @Transactional
    @Rollback(false)
    public void testDelete() {
        lineItemRepository.deleteById(lineItem11.getId());
        lineItemRepository.deleteById(lineItem12.getId());
        lineItemRepository.deleteById(lineItem13.getId());
        lineItemRepository.deleteById(lineItem14.getId());

        lineItemRepository.deleteById(lineItem21.getId());
        lineItemRepository.deleteById(lineItem22.getId());
        lineItemRepository.deleteById(lineItem23.getId());
        lineItemRepository.deleteById(lineItem24.getId());

        inquiryRepository.deleteById(inquiry1.getId());
        inquiryRepository.deleteById(inquiry2.getId());
    }
}
