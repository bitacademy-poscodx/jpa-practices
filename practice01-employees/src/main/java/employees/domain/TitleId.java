package employees.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@Entity
public class TitleId implements Serializable {
    private static final long serialVersionUID = -3877332280974629271L;
}