package inquiry.repository;

import inquiry.domain.CarLineItem;
import inquiry.domain.ColdRolledLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColdRolledLineItemRepository extends JpaRepository<ColdRolledLineItem, Long> {
}
