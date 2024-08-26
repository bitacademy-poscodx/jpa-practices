package inquiry.repository;

import inquiry.domain.CarLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarLineItemRepository extends JpaRepository<CarLineItem, Long> {
}
