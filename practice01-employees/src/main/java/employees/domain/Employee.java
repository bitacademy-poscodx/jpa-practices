package employees.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer id;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", nullable = false, length = 14)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    @Lob
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "empNo")
    private Set<Salary> salaries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "empNo")
    private Set<Title> titles = new LinkedHashSet<>();

}