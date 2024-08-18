package ex11.domain.dto;

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