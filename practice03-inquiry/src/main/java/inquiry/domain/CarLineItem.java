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
@Table(name = "car_line_items")
@DiscriminatorValue(ProductType.NAMES.CAR)
public class CarLineItem extends LineItem {
    @Column(name = "part_name", nullable = false, length = 100)
    private String partName;

    @Column(name = "sales_vehicle_name", nullable = false, length = 100)
    private String salesVehicleName;

    @Builder
    CarLineItem(Integer orderNumber, String partName, String salesVehicleName) {
        super(true, orderNumber);

        this.partName = partName;
        this.salesVehicleName = salesVehicleName;
    }

    @Override
    public String toString() {
        return "CarLineItem{" +
                "partName='" + partName + '\'' +
                ", salesVehicleName='" + salesVehicleName + '\'' +
                ", " + super.toString() +
                '}';
    }
}
