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

    @Builder
    Inquiry(String corporate, String salesPerson) {
        this.corporate = corporate;
        this.salesPerson = salesPerson;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "id=" + id +
                ", corporate='" + corporate + '\'' +
                ", salesPerson='" + salesPerson + '\'' +
                '}';
    }
}