package inquiry.repository;

import inquiry.domain.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Long>, LineItemRepositoryCustom{
}
