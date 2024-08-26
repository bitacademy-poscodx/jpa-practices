package inquiry.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cold_rolled_line_items")
public class ColdRolledLineItem {
    @Id
    private Long id;

    @Column(name = "thickness", nullable = false, length = 10)
    private String thickness;

    @Column(name = "width", nullable = false, length = 10)
    private String width;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private LineItem lineItem;

    @Builder
    ColdRolledLineItem(String thickness, String width, Integer quantity) {
        this.thickness = thickness;
        this.width = width;
        this.quantity = quantity;
    }
}