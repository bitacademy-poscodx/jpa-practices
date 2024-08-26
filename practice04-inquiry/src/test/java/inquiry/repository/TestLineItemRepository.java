package inquiry.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inquiry.domain.CarLineItem;
import inquiry.domain.ColdRolledLineItem;
import inquiry.domain.Inquiry;
import inquiry.domain.LineItem;
import inquiry.domain.dto.InquiryDto;
import inquiry.domain.type.ProductType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLineItemRepository {

    private static final LineItem lineItem11 = LineItem.builder().orderNumber(1).productType(ProductType.CAR).isActivated(true).build();
    private static final LineItem lineItem12 = LineItem.builder().orderNumber(2).productType(ProductType.CAR).isActivated(true).build();
    private static final LineItem lineItem13 = LineItem.builder().orderNumber(3).productType(ProductType.CAR).isActivated(true).build();
    private static final LineItem lineItem21 = LineItem.builder().orderNumber(1).productType(ProductType.COLD_ROLLED).isActivated(true).build();
    private static final LineItem lineItem22 = LineItem.builder().orderNumber(2).productType(ProductType.COLD_ROLLED).isActivated(true).build();
    private static final LineItem lineItem23 = LineItem.builder().orderNumber(3).productType(ProductType.COLD_ROLLED).isActivated(true).build();
    private static final LineItem lineItem24 = LineItem.builder().orderNumber(4).productType(ProductType.COLD_ROLLED).isActivated(true).build();

    private static final CarLineItem carLineItem1 = CarLineItem.builder().partName("partName02").salesVehicleName("salesVehicleName02").build();
    private static final CarLineItem carLineItem2 = CarLineItem.builder().partName("partName03").salesVehicleName("salesVehicleName03").build();
    private static final CarLineItem carLineItem3 = CarLineItem.builder().partName("partName04").salesVehicleName("salesVehicleName04").build();

    private static final ColdRolledLineItem coldRolledLineItem1 = ColdRolledLineItem.builder().width("1 mm").thickness("1 inch").quantity(1).build();
    private static final ColdRolledLineItem coldRolledLineItem2 = ColdRolledLineItem.builder().width("2 mm").thickness("2 inch").quantity(2).build();
    private static final ColdRolledLineItem coldRolledLineItem3 = ColdRolledLineItem.builder().width("3 mm").thickness("3 inch").quantity(3).build();
    private static final ColdRolledLineItem coldRolledLineItem4 = ColdRolledLineItem.builder().width("4 mm").thickness("4 inch").quantity(4).build();

    private static final Inquiry inquiry1 = Inquiry.builder().corporate("company1").salesPerson("영업사원1").build();
    private static final Inquiry inquiry2 = Inquiry.builder().corporate("company2").salesPerson("영업사원3").build();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private CarLineItemRepository carLineItemRepository;

    @Autowired
    private ColdRolledLineItemRepository coldRolledLineItemRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void testSave() {
        assertThat(inquiryRepository.save(inquiry1).getId()).isNotNull();
        assertThat(inquiryRepository.save(inquiry2).getId()).isNotNull();

        lineItem11.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem11).getId()).isNotNull();
        carLineItem1.setLineItem(lineItem11);
        assertThat(carLineItemRepository.save(carLineItem1).getId()).isNotNull();

        lineItem12.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem12).getId()).isNotNull();
        carLineItem2.setLineItem(lineItem12);
        assertThat(carLineItemRepository.save(carLineItem2).getId()).isNotNull();

        lineItem13.setInquiry(inquiry1);
        assertThat(lineItemRepository.save(lineItem13).getId()).isNotNull();
        carLineItem3.setLineItem(lineItem13);
        assertThat(carLineItemRepository.save(carLineItem3).getId()).isNotNull();

        // ====================================================================================

        lineItem21.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem21).getId()).isNotNull();
        coldRolledLineItem1.setLineItem(lineItem21);
        assertThat(coldRolledLineItemRepository.save(coldRolledLineItem1).getId()).isNotNull();

        lineItem22.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem22).getId()).isNotNull();
        coldRolledLineItem2.setLineItem(lineItem22);
        assertThat(coldRolledLineItemRepository.save(coldRolledLineItem2).getId()).isNotNull();

        lineItem23.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem23).getId()).isNotNull();
        coldRolledLineItem3.setLineItem(lineItem23);
        assertThat(coldRolledLineItemRepository.save(coldRolledLineItem3).getId()).isNotNull();

        lineItem24.setInquiry(inquiry2);
        assertThat(lineItemRepository.save(lineItem24).getId()).isNotNull();
        coldRolledLineItem4.setLineItem(lineItem24);
        assertThat(coldRolledLineItemRepository.save(coldRolledLineItem4).getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void testFindById() throws JsonProcessingException {
        LineItem lineItem = lineItemRepository.findById(lineItem11.getId()).orElse(null);
        Assertions.assertNotNull(lineItem);

        log.info("Entity <LineItem>: {}>>>", objectMapper.writeValueAsString(lineItem));
    }

    @Test
    @Order(3)
    @Transactional
    public void testFindAll02() throws JsonProcessingException {
        List<LineItem> list = lineItemRepository.findAll02();
        // assertThat(list.size()).isEqualTo(countOfLineItems.intValue());
        log.info("List<LineItem>: {}", objectMapper.writeValueAsString(list));
    }

    @Test
    @Order(4)
    public void testFindAll03() throws JsonProcessingException {
        List<InquiryDto> list = lineItemRepository.findAll03();
        // assertThat(list.size()).isEqualTo(countOfLineItems.intValue());
        log.info("List<InquiryDto>: {}", objectMapper.writeValueAsString(list));
    }
}
