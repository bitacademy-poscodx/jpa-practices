package employees.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "salaries")
public class Salary {
    @EmbeddedId
    private SalaryId id;




    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

}