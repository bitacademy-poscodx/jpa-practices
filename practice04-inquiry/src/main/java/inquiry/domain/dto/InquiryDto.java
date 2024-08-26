package inquiry.domain.dto;

import inquiry.domain.CarLineItem;
import inquiry.domain.ColdRolledLineItem;
import inquiry.domain.LineItem;
import inquiry.domain.type.ProductType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
public class InquiryDto {
    private Long id;
    private String corporate;
    private String salesPerson;

    private Long lineItemId;
    private ProductType productType;
    private Boolean isActivated;
    private Integer orderNumer;

    private CarLineItem car;
    private ColdRolledLineItem coldRolled;

    public InquiryDto (LineItem lineItem) {
        this.id = lineItem.getInquiry().getId();
        this.corporate = lineItem.getInquiry().getCorporate();
        this.salesPerson = lineItem.getInquiry().getSalesPerson();

        this.lineItemId = lineItem.getId();
        this.productType = lineItem.getProductType();
        this.isActivated = lineItem.getIsActivated();
        this.orderNumer = lineItem.getOrderNumber();

        this.car = lineItem.getCar();
        this.coldRolled = lineItem.getColdRolled();
    }
}
