package inquiry.repository;

import inquiry.domain.LineItem;
import inquiry.domain.dto.InquiryDto;

import java.util.List;

public interface LineItemRepositoryCustom {
    List<LineItem> findAll02();
    List<InquiryDto> findAll03();
}
