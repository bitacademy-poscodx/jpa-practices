package inquiry.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inquiry.domain.Inquiry;
import static inquiry.domain.QInquiry.inquiry;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class InquiryRepositoryImpl extends QuerydslRepositorySupport implements InquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public InquiryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Inquiry.class);
        this.queryFactory = queryFactory;
    }

    public List<Inquiry> findAll02() {
        return queryFactory
                .selectDistinct(inquiry)
                .from(inquiry)
                .fetchJoin()
                .fetch();
    }



}
