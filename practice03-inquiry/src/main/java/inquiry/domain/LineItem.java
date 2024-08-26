package inquiry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"id", "productType", "isActivated", "orderNumber", "inquiry"})
@Entity
@Table(name = "line_items")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_type", nullable = false, insertable=false, updatable=false)
    private String productType;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="inquiry_id")
    private Inquiry inquiry;

    public LineItem(Boolean isActivated, Integer orderNumber) {
        this.isActivated = isActivated;
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", isActivated=" + isActivated +
                ", orderNumber=" + orderNumber +
                ", inquiry=" + inquiry +
                '}';
    }
}