package inquiry.domain;

import inquiry.domain.type.ProductType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cold_rolled_line_items")
@DiscriminatorValue(ProductType.NAMES.COLD_ROLLED)
public class ColdRolledLineItem extends LineItem {
    @Column(name = "thickness", nullable = false, length = 10)
    private String thickness;

    @Column(name = "width", nullable = false, length = 10)
    private String width;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Builder
    ColdRolledLineItem(Integer orderNumber, String thickness, String width, Integer quantity) {
        super(true, orderNumber);

        this.thickness = thickness;
        this.width = width;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ColdRolledLineItem{" +
                "thickness='" + thickness + '\'' +
                ", width='" + width + '\'' +
                ", quantity=" + quantity +
                ", " + super.toString() +
                '}';
    }
}