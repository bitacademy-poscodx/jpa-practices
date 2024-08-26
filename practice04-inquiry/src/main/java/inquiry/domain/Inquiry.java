package inquiry.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inquiry")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "corporate", nullable = false, length = 100)
    private String corporate;

    @Column(name = "sales_person", nullable = false, length = 50)
    private String salesPerson;

    // @OneToMany(mappedBy = "inquiry", fetch = FetchType.LAZY)
    // @SQLRestriction("order_number = 1")
    // private List<LineItem> lineItems = new ArrayList<>();

    @Builder
    Inquiry(String corporate, String salesPerson) {
        this.corporate = corporate;
        this.salesPerson = salesPerson;
    }
}