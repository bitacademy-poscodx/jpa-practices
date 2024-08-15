package ex11.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Integer bookNo;
    private String bookTitle;
    private Integer bookPrice;
    private Integer quantity;

}