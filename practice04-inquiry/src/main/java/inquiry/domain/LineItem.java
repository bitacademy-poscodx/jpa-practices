package inquiry.domain;

import inquiry.domain.type.ProductType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "line_items")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @OneToOne(mappedBy = "lineItem")
    private CarLineItem car;

    @OneToOne(mappedBy = "lineItem")
    private ColdRolledLineItem coldRolled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="inquiry_id")
    private Inquiry inquiry;

    @Builder
    LineItem(Boolean isActivated, Integer orderNumber, ProductType productType) {
        this.productType = productType;
        this.isActivated = isActivated;
        this.orderNumber = orderNumber;
    }
}