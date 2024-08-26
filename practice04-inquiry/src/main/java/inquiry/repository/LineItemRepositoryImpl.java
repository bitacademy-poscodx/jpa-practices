package inquiry.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inquiry.domain.LineItem;
import inquiry.domain.dto.InquiryDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;
import static inquiry.domain.QColdRolledLineItem.coldRolledLineItem;
import static inquiry.domain.QLineItem.lineItem;

public class LineItemRepositoryImpl extends QuerydslRepositorySupport implements LineItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LineItemRepositoryImpl(JPAQueryFactory queryFactory) {
        super(LineItem.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<LineItem> findAll02() {
        return queryFactory
                .selectFrom(lineItem)
                .where(lineItem.orderNumber.eq(1))

                .leftJoin(lineItem.car)
                .fetchJoin()

                .leftJoin(lineItem.coldRolled)
                .fetchJoin()

                .innerJoin(lineItem.inquiry)
                .fetchJoin()

                .fetch();
    }

    @Override
    public List<InquiryDto> findAll03() {
        return queryFactory
                .select(Projections.constructor(InquiryDto.class, lineItem))
                .from(lineItem)
                .where(lineItem.orderNumber.eq(1))

                .leftJoin(lineItem.car)
                .fetchJoin()

                .leftJoin(lineItem.coldRolled, coldRolledLineItem)
                .fetchJoin()

                .innerJoin(lineItem.inquiry)
                .fetchJoin()

                .fetch();
    }
}
