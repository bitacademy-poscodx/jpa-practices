package ex03.domain.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrdersCountOfUserDto {
    private Integer userId;
    private Long orderCount;

}
