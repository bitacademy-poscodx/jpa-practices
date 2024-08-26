package inquiry.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "car_line_items")
public class CarLineItem {
    @Id
    private Long id;

    @Column(name = "part_name", nullable = false, length = 100)
    private String partName;

    @Column(name = "sales_vehicle_name", nullable = false, length = 100)
    private String salesVehicleName;

    @JsonIgnore
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private LineItem lineItem;

    @Builder
    CarLineItem(String partName, String salesVehicleName) {
        this.partName = partName;
        this.salesVehicleName = salesVehicleName;
    }

}
