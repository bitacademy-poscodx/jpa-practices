package ex02.domain.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private Integer hit;
    private String title;
    private String contents;
    private Date regDate;
    private String userName;
}
